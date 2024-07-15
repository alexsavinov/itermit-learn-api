package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.QuestionSet;
import com.itermit.learn.model.entity.Session;
import com.itermit.learn.model.entity.User;
import com.itermit.learn.utils.SpecificationUtils;
import jakarta.persistence.criteria.Join;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class SessionSpecs {

    public static Specification<Session> filter(Map<String, String> params) {
        Specification<Session> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));
        spec = SpecificationUtils.addSpecification(spec, getUserSpecification(params.get("userId")));
        spec = SpecificationUtils.addSpecification(spec, getQuestionSetSpecification(params.get("setId")));
        spec = SpecificationUtils.addSpecification(spec, getFinishedSpecification(params.get("finished")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<Session> getSearchSpecification(String text) {
        Specification<Session> specification = null;

        if (ofNullable(text).isPresent()) {
            if (text.matches("\\d+")) {
                specification = (root, query, builder) ->
                        builder.in(root.get("id")).value(Collections.singleton(text)
                );
            }
        }
        return specification;
    }

    private static @Nullable Specification<Session> getUserSpecification(String id) {
        Specification<Session> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<Session, User> join = root.join("user");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<Session> getQuestionSetSpecification(String id) {
        Specification<Session> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<Session, QuestionSet> join = root.join("questionSet");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<Session> getFinishedSpecification(String finished) {
        Specification<Session> specification = null;

        if (ofNullable(finished).isPresent()) {
            if (Boolean.parseBoolean(finished)) {
                specification = (root, query, builder) -> builder.isNotNull(root.get("finishedDate"));
            } else {
                specification = (root, query, builder) -> builder.isNull(root.get("finishedDate"));
            }
        }

        return specification;
    }
}
