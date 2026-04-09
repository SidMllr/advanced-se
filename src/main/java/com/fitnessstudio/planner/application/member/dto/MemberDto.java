package com.fitnessstudio.planner.application.member.dto;

import com.fitnessstudio.planner.domain.model.member.Member;

public record MemberDto(String id, String name, String email, String phoneNumber) {

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId().toString(),
                member.getName(),
                member.getContactInfo().email(),
                member.getContactInfo().phoneNumber()
        );
    }
}
