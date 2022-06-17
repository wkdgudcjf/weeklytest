package com.example.spring0617.auth.service;

import com.example.spring0617.auth.dto.RequestLoginMemberDTO;
import com.example.spring0617.common.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    String login(HttpServletRequest request, HttpServletResponse response, RequestLoginMemberDTO requestLoginMemberDTO);
    ApiResponse refresh(HttpServletRequest request, HttpServletResponse response);
}
