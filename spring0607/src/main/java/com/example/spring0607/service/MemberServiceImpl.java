package com.example.spring0607.service;

import com.example.spring0607.dto.RequestCreateMemberDTO;
import com.example.spring0607.dto.RequestUpdateMemberDTO;
import com.example.spring0607.dto.ResponseMemberDTO;
import com.example.spring0607.entity.Gender;
import com.example.spring0607.entity.Member;
import com.example.spring0607.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    public long saveMember(RequestCreateMemberDTO requestCreateMemberDTO) {
        Member member = Member.builder()
                .email(requestCreateMemberDTO.getEmail())
                .name(requestCreateMemberDTO.getName())
                .age(requestCreateMemberDTO.getAge())
                .gender(requestCreateMemberDTO.getGender() == 0 ? Gender.M : Gender.F)
                .build();
        return memberRepository.save(member).getIdx();
    }

    @Override
    public ResponseMemberDTO findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        return ResponseMemberDTO.builder()
                .email(member.getMyServiceEmail())
                .name(member.getMyServiceName())
                .age(member.getMyServiceAge())
                .gender(member.getMyServiceGender() == Gender.M ? 0 : 1)
                .build();
    }

    @Override
    public long modifyMember(Long id, RequestUpdateMemberDTO requestUpdateMemberDTO) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        member.updateProfile(requestUpdateMemberDTO);
        return memberRepository.save(member).getIdx();
    }

    @Override
    public long removeMember(Long id) {
        memberRepository.deleteById(id);
        return id;
    }
    @Transactional
    @Override
    public List<ResponseMemberDTO> recommendMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        List<Member> memberList = memberRepository.findAllByMyServiceAgeEqualsAndMyServiceGenderNot(member.getMyServiceAge(),member.getMyServiceGender());
        List<ResponseMemberDTO> resultList = new LinkedList<ResponseMemberDTO>();
        for(Member matchMember : memberList)
        {
            resultList.add(ResponseMemberDTO.builder()
                    .email(matchMember.getMyServiceEmail())
                    .name(matchMember.getMyServiceName())
                    .age(matchMember.getMyServiceAge())
                    .gender(matchMember.getMyServiceGender() == Gender.M ? 0 : 1)
                    .build());
        }
        return resultList;
    }
}
