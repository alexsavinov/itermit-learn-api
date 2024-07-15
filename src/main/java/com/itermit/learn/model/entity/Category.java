package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(unique = true)
    private String name;
}
