package com.itermit.learn.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itermit.learn.model.EGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "profile")
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 80)
    private String name;

    @Size(max = 80)
    private String surname;

    private EGender gender;

    @Size(max = 50)
    private String city;

    @Size(max = 150)
    private String address;

    @Size(max = 100)
    private String company;

    @Size(max = 10)
    private String mobile;

    @Size(max = 10)
    private String tele;

    @Size(max = 50)
    private String website;

    @Size(max = 50)
    private String date;

    @Size(max = 100)
    private String avatar;

    @OneToOne(mappedBy = "profile")
    private User user;

    @PreUpdate
    protected void preUpdate() {
        user.setLastUpdateDate();
    }
}
