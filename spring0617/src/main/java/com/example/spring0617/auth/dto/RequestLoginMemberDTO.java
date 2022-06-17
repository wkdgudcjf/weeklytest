package com.example.spring0617.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestLoginMemberDTO {
    private String email;

    @Builder
    public RequestLoginMemberDTO(String email) {
        this.email = email;
    }
}
