package com.itermit.learn.repository.specification;

import com.itermit.learn.model.entity.Article;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecs {

    public static Specification<Article> containsTextInName(String text) {
        String finalText = "%" + text + "%";

        Specification<Article> specification = (root, query, builder) -> builder.or(
                builder.like(root.get("title"), finalText),
                builder.like(root.get("description"), finalText)
        );

        return specification;
    }
}
