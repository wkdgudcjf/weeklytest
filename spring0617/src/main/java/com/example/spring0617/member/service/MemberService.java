package com.example.spring0617.member.service;

import com.example.spring0617.member.dto.RequestCreateMemberDTO;
import com.example.spring0617.member.dto.RequestUpdateMemberDTO;
import com.example.spring0617.member.dto.ResponseMemberDTO;

import java.util.List;

public interface MemberService {
    long saveMember(RequestCreateMemberDTO requestCreateMemberDTO);
    ResponseMemberDTO findMember(Long id);
    long removeMember(Long id);
    long modifyMember(Long id, RequestUpdateMemberDTO requestUpdateMemberDTO);
    List<ResponseMemberDTO> recommendMember(Long id);
}
