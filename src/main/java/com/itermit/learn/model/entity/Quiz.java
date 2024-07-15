package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Quiz extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_id")
    private List<QuizAnswer> quizAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    public Source source;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
