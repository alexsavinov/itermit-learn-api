package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Question extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
