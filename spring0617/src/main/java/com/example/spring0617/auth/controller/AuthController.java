package com.example.spring0617.auth.controller;

import com.example.spring0617.auth.dto.RequestLoginMemberDTO;
import com.example.spring0617.auth.service.AuthService;
import com.example.spring0617.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse loginMember(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestLoginMemberDTO requestLoginMemberDTO)
    {
        String token =  authService.login(request, response, requestLoginMemberDTO);
        return ApiResponse.success("result",token);
    }

    @GetMapping("/refresh")
    public ApiResponse refreshMember(HttpServletRequest request, HttpServletResponse response)
    {
        return authService.refresh(request, response);
    }
}
