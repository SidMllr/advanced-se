package com.fitnessstudio.planner.infrastructure.persistence.adapter;

import com.fitnessstudio.planner.domain.model.member.Member;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.repository.MemberRepository;
import com.fitnessstudio.planner.infrastructure.persistence.jpa.MemberJpaRepository;
import com.fitnessstudio.planner.infrastructure.persistence.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryAdapter implements MemberRepository {

    private final MemberJpaRepository jpaRepository;
    private final MemberMapper mapper;

    public MemberRepositoryAdapter(MemberJpaRepository jpaRepository, MemberMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Member save(Member member) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(member)));
    }

    @Override
    public Optional<Member> findById(MemberId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public List<Member> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(MemberId id) {
        jpaRepository.deleteById(id.value());
    }
}
