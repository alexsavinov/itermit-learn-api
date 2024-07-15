package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.Article;
import com.itermit.learn.model.entity.User;
import com.itermit.learn.utils.SpecificationUtils;
import jakarta.persistence.criteria.Join;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class ArticleSpecs {

    public static Specification<Article> filter(Map<String, String> params) {
        Specification<Article> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));
        spec = SpecificationUtils.addSpecification(spec, getUserSpecification(params.get("userId")));
        spec = SpecificationUtils.addSpecification(spec, getVisibleSpecification(params.get("visible")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<Article> getSearchSpecification(String text) {
        Specification<Article> specification = null;

        if (ofNullable(text).isPresent()) {
            String finalText = "%" + text + "%";
            specification = (root, query, builder) -> builder.or(
                    builder.like(builder.upper(root.get("title")), finalText.toUpperCase()),
                    builder.like(builder.upper(root.get("description")), finalText.toUpperCase())
            );

            if (text.matches("\\d+")) {
                specification = specification.or((root, query, builder) ->
                        builder.in(root.get("id")).value(Collections.singleton(text))
                );
            }
        }
        return specification;
    }

    private static @Nullable Specification<Article> getUserSpecification(String id) {
        Specification<Article> specification = null;

        if (ofNullable(id).isPresent()) {
            specification = (root, query, builder) -> {
                Join<Article, User> join = root.join("author");
                return builder.equal(join.<Long>get("id"), id);
            };
        }

        return specification;
    }

    private static @Nullable Specification<Article> getVisibleSpecification(String custom) {
        Specification<Article> specification = null;

        if (ofNullable(custom).isPresent()) {
            specification = (root, query, builder) -> builder.equal(root.get("visible"), Boolean.parseBoolean(custom));
        }

        return specification;
    }
}
