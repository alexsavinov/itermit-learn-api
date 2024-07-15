package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "quizanswer")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

    private int sequence;

    private boolean correct;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_session_quizanswer",
            joinColumns = @JoinColumn(name = "quizanswer_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private List<Session> sessions;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;
}
