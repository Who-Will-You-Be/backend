package com.example.data_analysisbackend.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Getter
@NoArgsConstructor
public class Session {

    @Id
    @Column(name = "session_id", length = 128)
    private String sessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Builder
    public Session(String sessionId, User user, LocalDateTime expiresAt) {
        this.sessionId = sessionId;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
