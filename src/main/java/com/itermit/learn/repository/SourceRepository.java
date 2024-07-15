package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
}
