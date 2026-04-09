package com.fitnessstudio.planner.application.member;

import com.fitnessstudio.planner.application.exception.MemberNotFoundException;
import com.fitnessstudio.planner.application.member.dto.CreateMemberCommand;
import com.fitnessstudio.planner.application.member.dto.MemberDto;
import com.fitnessstudio.planner.domain.model.member.ContactInfo;
import com.fitnessstudio.planner.domain.model.member.Member;
import com.fitnessstudio.planner.domain.model.member.MemberId;
import com.fitnessstudio.planner.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MemberApplicationService {

    private final MemberRepository memberRepository;

    public MemberApplicationService(MemberRepository memberRepository) {
        this.memberRepository = Objects.requireNonNull(memberRepository);
    }

    public MemberDto createMember(CreateMemberCommand command) {
        Objects.requireNonNull(command, "Command must not be null");
        ContactInfo contactInfo = ContactInfo.of(command.email(), command.phoneNumber());
        Member member = Member.create(command.name(), contactInfo);
        return MemberDto.from(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public MemberDto getMember(String id) {
        return memberRepository.findById(MemberId.of(id))
                .map(MemberDto::from)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto::from)
                .toList();
    }

    public void deleteMember(String id) {
        memberRepository.findById(MemberId.of(id))
                .orElseThrow(() -> new MemberNotFoundException(id));
        memberRepository.deleteById(MemberId.of(id));
    }
}
