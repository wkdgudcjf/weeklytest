package com.example.spring0607.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateMemberDTO {
    private String name;
    private int age;
    private int gender;

    @Builder
    public RequestUpdateMemberDTO(String name, int age, int gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
