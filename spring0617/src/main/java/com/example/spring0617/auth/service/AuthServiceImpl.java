package com.example.spring0617.auth.service;

import com.example.spring0617.auth.config.properties.AppProperties;
import com.example.spring0617.auth.dto.RequestLoginMemberDTO;
import com.example.spring0617.auth.util.CookieUtil;
import com.example.spring0617.auth.util.HeaderUtil;
import com.example.spring0617.auth.util.token.AuthToken;
import com.example.spring0617.auth.util.token.AuthTokenProvider;
import com.example.spring0617.common.ApiResponse;
import com.example.spring0617.member.entity.Member;
import com.example.spring0617.member.entity.RoleType;
import com.example.spring0617.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final static long THREE_DAYS_MSEC = 259200000;

    @Transactional
    @Override
    public String login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestLoginMemberDTO requestLoginMemberDTO) {
        Member member = memberRepository.findByMyServiceEmail(requestLoginMemberDTO.getEmail());
        if(member == null) return null;

        Date now = new Date();
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        member.updateRefreshToken(refreshToken.getToken());

        AuthToken accessToken = tokenProvider.createAuthToken(
                member.getIdx(),
                member.getMyServiceName(),
                member.getMyServiceRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        int cookieMaxAge = (int) refreshTokenExpiry / 1000;
        CookieUtil.deleteCookie(httpRequest, httpResponse, AuthToken.REFRESH_TOKEN, "localhost");
        CookieUtil.addCookie(httpResponse, AuthToken.REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge, "localhost");

        return accessToken.getToken();
    }

    @Transactional
    @Override
    public ApiResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String name = claims.getSubject();
        long id = (long)claims.get(AuthToken.MEMBER_ID);
        RoleType roleType = RoleType.of(claims.get(AuthToken.AUTHORITIES_KEY, String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, AuthToken.REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        String userRefreshToken = member.getMyServiceRefreshToken();
        if (!userRefreshToken.equals(refreshToken)) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                id,
                name,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            member.updateRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, AuthToken.REFRESH_TOKEN,"localhost");
            CookieUtil.addCookie(response, AuthToken.REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge,"localhost");
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }
}
