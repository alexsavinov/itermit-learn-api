package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.User;
import com.itermit.learn.utils.SpecificationUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class UserSpecs {

    public static Specification<User> filter(Map<String, String> params) {
        Specification<User> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<User> getSearchSpecification(String text) {
        Specification<User> specification = null;

        if (ofNullable(text).isPresent()) {
            String finalText = "%" + text + "%";
            specification = (root, query, builder) -> builder.or(
                    builder.like(builder.upper(root.get("username")), finalText.toUpperCase()),
                    builder.like(builder.upper(root.get("profile").get("name")), finalText.toUpperCase()),
                    builder.like(builder.upper(root.get("profile").get("surname")), finalText.toUpperCase())
            );

            if (text.matches("\\d+")) {
                specification = specification.or((root, query, builder) ->
                        builder.in(root.get("id")).value(Collections.singleton(text))
                );
            }
        }
        return specification;
    }
}
