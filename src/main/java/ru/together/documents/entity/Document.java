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
    private LibUser libUser;

    @Column(unique = false, nullable = false)
    private String file_name;

    @Column(unique = false, nullable = true)
    private String file_url;

    @Column(nullable = true, length = 255)
    private String title;

    @Column(nullable = true, length = 255)
    private String author;

    @Column(nullable = true, length = 2048)
    private String description;

    @Column(nullable = false)
    private boolean isPublic = true;

    @Column(unique = false, nullable = false)
    private Instant createdAt;
}
