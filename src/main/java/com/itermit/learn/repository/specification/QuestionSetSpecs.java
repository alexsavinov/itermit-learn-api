package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.Category;
import com.itermit.learn.model.entity.QuestionSet;
import com.itermit.learn.model.entity.User;
import com.itermit.learn.utils.SpecificationUtils;
import jakarta.persistence.criteria.Join;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class QuestionSetSpecs {

    public static Specification<QuestionSet> filter(Map<String, String> params) {
        Specification<QuestionSet> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));
        spec = SpecificationUtils.addSpecification(spec, getCategorySpecification(params.get("categoryId")));
        spec = SpecificationUtils.addSpecification(spec, getUserSpecification(params.get("userId")));
        spec = SpecificationUtils.addSpecification(spec, getCustomSpecification(params.get("custom")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<QuestionSet> getSearchSpecification(String text) {
        Specification<QuestionSet> specification = null;

        if (ofNullable(text).isPresent()) {
            String finalText = "%" + text + "%";
            specification = (root, query, builder) -> builder.or(
                    builder.like(builder.upper(root.get("name")), finalText.toUpperCase())
            );

            if (text.matches("\\d+")) {
                specification = specification.or((root, query, builder) ->
                        builder.in(root.get("id")).value(Collections.singleton(text))
                );
            }
        }
        return specification;
    }

    private static @Nullable Specification<QuestionSet> getCategorySpecification(String id) {
        Specification<QuestionSet> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<QuestionSet, Category> join = root.join("category");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<QuestionSet> getUserSpecification(String id) {
        Specification<QuestionSet> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<QuestionSet, User> join = root.join("user");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<QuestionSet> getCustomSpecification(String custom) {
        Specification<QuestionSet> specification = null;

        if (ofNullable(custom).isPresent()) {
            specification = (root, query, builder) -> builder.equal(root.get("custom"), Boolean.parseBoolean(custom));
        }

        return specification;
    }

}
