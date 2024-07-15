package com.itermit.learn.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdDate;

    @Column(name = "last_update_date")
    @LastModifiedDate
    private Instant lastUpdateDate;

    public void setLastUpdateDate() {
        this.lastUpdateDate = Instant.now();
    }
 }
