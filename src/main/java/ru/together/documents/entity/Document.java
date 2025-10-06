package ru.together.documents.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "documents")
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = false, nullable = false)
    private String file_name;

    @Column(unique = false, nullable = true)
    private String file_url;

    @Column(unique = false, nullable = false)
    private Instant createdAt;
}
