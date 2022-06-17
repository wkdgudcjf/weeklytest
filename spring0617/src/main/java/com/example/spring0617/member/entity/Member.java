package com.example.spring0617.member.entity;

import javax.persistence.*;

import com.example.spring0617.member.dto.RequestUpdateMemberDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false)
    private String myServiceEmail;

    @Column(nullable = false)
    private String myServiceName;

    @Column(nullable = false)
    private int myServiceAge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender myServiceGender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType myServiceRoleType;

    @Column(nullable = false)
    private String myServiceRefreshToken;

    @Builder
    public Member(String email, String name, int age, Gender gender, RoleType roleType) {
        this.myServiceEmail = email;
        this.myServiceName = name;
        this.myServiceAge = age;
        this.myServiceGender = gender;
        this.myServiceRoleType = roleType;
    }

    public void updateRefreshToken(String refreshToken)
    {
        this.myServiceRefreshToken = refreshToken;
    }
    public void updateProfile(RequestUpdateMemberDTO requestUpdateMemberDTO) {
        this.myServiceName = requestUpdateMemberDTO.getName();
        this.myServiceAge = requestUpdateMemberDTO.getAge();
        this.myServiceGender =requestUpdateMemberDTO.getGender() == 0 ? Gender.M : Gender.F;
    }
}