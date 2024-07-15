package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.Source;
import com.itermit.learn.utils.SpecificationUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;


public class SourceSpecs {

    public static Specification<Source> filter(Map<String, String> params) {
        Specification<Source> spec;

        spec = SpecificationUtils.addSpecification(null, getSearchSpecification(params.get("search")));

        if (ofNullable(spec).isEmpty()) {
            spec = (root, query, cb) -> cb.isTrue(cb.literal(true));
        }

        return spec;
    }

    private static @Nullable Specification<Source> getSearchSpecification(String text) {
        Specification<Source> specification = null;

        if (ofNullable(text).isPresent()) {
            String finalText = "%" + text + "%";
            specification = (root, query, builder) -> builder.or(
                    builder.like(builder.upper(root.get("name")), finalText.toUpperCase()),
                    builder.like(builder.upper(root.get("url")), finalText.toUpperCase())
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
