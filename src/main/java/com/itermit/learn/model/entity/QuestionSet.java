package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questionset")
public class QuestionSet extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean custom;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "join_questionset_quizanswer",
            joinColumns = @JoinColumn(name = "questionset_id"),
            inverseJoinColumns = @JoinColumn(name = "quizanswer_id")
    )
    private List<Quiz> quizzes;

    @ManyToMany
    @JoinTable(
            name = "join_questionset_answer",
            joinColumns = @JoinColumn(name = "questionset_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Question> questions;
}
