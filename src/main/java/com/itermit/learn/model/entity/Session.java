package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Session extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant finishedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "questionset_id")
    private QuestionSet questionSet;

    @ManyToMany
    @JoinTable(
            name = "join_session_quizanswer",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "quizanswer_id")
    )
    private Set<QuizAnswer> quizAnswers;

    @OneToMany
    @JoinColumn(name = "session_id")
    private Set<UserAnswer> userAnswers;
}
