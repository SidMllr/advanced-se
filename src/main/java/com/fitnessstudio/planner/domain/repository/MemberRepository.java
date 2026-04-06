package com.fitnessstudio.planner.domain.repository;

import com.fitnessstudio.planner.domain.model.member.Member;
import com.fitnessstudio.planner.domain.model.member.MemberId;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(MemberId id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    void deleteById(MemberId id);
}
