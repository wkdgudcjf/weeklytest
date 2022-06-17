package com.example.spring0617.member.repository;

import com.example.spring0617.member.entity.Gender;
import com.example.spring0617.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByMyServiceAgeEqualsAndMyServiceGenderNot(int myServiceAge, Gender myServiceGender);
    Member findByMyServiceEmail(String email);
}