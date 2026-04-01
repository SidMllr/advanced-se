package com.fitnessstudio.planner.domain.model.session;

import com.fitnessstudio.planner.domain.model.member.MemberId;

import java.time.LocalDateTime;
import java.util.Objects;

public class WaitlistEntry {

    private final WaitlistEntryId id;
    private final MemberId memberId;
    private final LocalDateTime addedAt;

    public WaitlistEntry(WaitlistEntryId id, MemberId memberId, LocalDateTime addedAt) {
        this.id = Objects.requireNonNull(id, "WaitlistEntryId must not be null");
        this.memberId = Objects.requireNonNull(memberId, "MemberId must not be null");
        this.addedAt = Objects.requireNonNull(addedAt, "AddedAt must not be null");
    }

    public static WaitlistEntry create(MemberId memberId) {
        return new WaitlistEntry(WaitlistEntryId.generate(), memberId, LocalDateTime.now());
    }

    public WaitlistEntryId getId() { return id; }
    public MemberId getMemberId() { return memberId; }
    public LocalDateTime getAddedAt() { return addedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaitlistEntry that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
