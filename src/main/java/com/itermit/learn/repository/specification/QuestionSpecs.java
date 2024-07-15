package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.Category;
import com.itermit.learn.model.entity.Question;
import com.itermit.learn.model.entity.Source;
import com.itermit.learn.utils.SpecificationUtils;
import jakarta.persistence.criteria.Join;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class QuestionSpecs {

    public static Specification<Question> filter(Map<String, String> params) {
        Specification<Question> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));
        spec = SpecificationUtils.addSpecification(spec, getCategorySpecification(params.get("categoryId")));
        spec = SpecificationUtils.addSpecification(spec, getSourceSpecification(params.get("sourceId")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<Question> getSearchSpecification(String text) {
        Specification<Question> specification = null;

        if (ofNullable(text).isPresent()) {
            String finalText = "%" + text + "%";
            specification = (root, query, builder) -> builder.or(
                    builder.like(builder.upper(root.get("title")), finalText.toUpperCase()),
                    builder.like(builder.upper(root.get("content")), finalText.toUpperCase())
            );

            if (text.matches("\\d+")) {
                specification = specification.or((root, query, builder) ->
                        builder.in(root.get("id")).value(Collections.singleton(text))
                );
            }
        }
        return specification;
    }

    private static @Nullable Specification<Question> getCategorySpecification(String id) {
        Specification<Question> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<Question, Category> join = root.join("category");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<Question> getSourceSpecification(String id) {
        Specification<Question> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<Question, Source> join = root.join("source");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }
}
