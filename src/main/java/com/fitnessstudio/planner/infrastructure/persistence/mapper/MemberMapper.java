package com.fitnessstudio.planner.infrastructure.persistence.mapper;

import com.fitnessstudio.planner.domain.model.member.ContactInfo;
import com.fitnessstudio.planner.domain.model.member.Member;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.infrastructure.persistence.entity.MemberJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberJpaEntity toJpaEntity(Member member) {
        return new MemberJpaEntity(
                member.getId().value(),
                member.getName(),
                member.getContactInfo().email(),
                member.getContactInfo().phoneNumber()
        );
    }

    public Member toDomain(MemberJpaEntity entity) {
        return new Member(
                MemberId.of(entity.getId()),
                entity.getName(),
                ContactInfo.of(entity.getEmail(), entity.getPhoneNumber())
        );
    }
}
