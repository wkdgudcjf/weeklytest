package com.example.spring0617.member.controller;

import com.example.spring0617.auth.config.MemberPrincial;
import com.example.spring0617.common.ApiResponse;
import com.example.spring0617.member.dto.RequestCreateMemberDTO;
import com.example.spring0617.member.dto.RequestUpdateMemberDTO;
import com.example.spring0617.member.dto.ResponseMemberDTO;
import com.example.spring0617.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ApiResponse createMember(@RequestBody RequestCreateMemberDTO requestCoupleDTO){
        long retId = memberService.saveMember(requestCoupleDTO);
        return ApiResponse.success("result",retId);
    }

    @Secured("ROLE_USER")
    @GetMapping("/read/")
    public ApiResponse readMember(MemberPrincial memberPrincial){
        ResponseMemberDTO responseMemberDTO = memberService.findMember(memberPrincial.getId());
        return ApiResponse.success("result",responseMemberDTO);
    }

    @Secured("ROLE_USER")
    @PutMapping("/update/{id}")
    public ApiResponse updateMember(MemberPrincial memberPrincial, @RequestBody RequestUpdateMemberDTO requestUpdateMemberDTO){
        long retId = memberService.modifyMember(memberPrincial.getId(), requestUpdateMemberDTO);
        return ApiResponse.success("result",retId);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/")
    public ApiResponse deleteMember(MemberPrincial memberPrincial){
        long retId = memberService.removeMember(memberPrincial.getId());
        return ApiResponse.success("result", retId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/recommend/")
    public ApiResponse recommendMember(MemberPrincial memberPrincial){
        List<ResponseMemberDTO> responseMemberDTOList = memberService.recommendMember(memberPrincial.getId());
        return ApiResponse.success("result",responseMemberDTOList);
    }
}
