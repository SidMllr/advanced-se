package com.fitnessstudio.planner.presentation.member;

import com.fitnessstudio.planner.application.member.MemberApplicationService;
import com.fitnessstudio.planner.application.member.dto.CreateMemberCommand;
import com.fitnessstudio.planner.application.member.dto.MemberDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberApplicationService memberService;

    public MemberController(MemberApplicationService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@Valid @RequestBody CreateMemberRequest request) {
        CreateMemberCommand command = new CreateMemberCommand(
                request.name(), request.email(), request.phoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateMemberRequest(
            @NotBlank String name,
            @NotBlank @Email String email,
            String phoneNumber
    ) {}
}
