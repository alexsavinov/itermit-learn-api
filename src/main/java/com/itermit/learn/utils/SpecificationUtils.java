package com.itermit.learn.utils;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;


public class SpecificationUtils {

    public static <T> Specification<T> addSpecification(@Nullable Specification<T> initSpec,
                                                        Specification<T> spec) {

        if (ofNullable(initSpec).isPresent()) {
            return initSpec.and(Specification.where(spec));
        }
        return spec;
    }
}
