package io.github.shirohoo.realworld.domain.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(
            """
                    SELECT a FROM Article a
                    WHERE (:tag IS NULL OR :tag IN (SELECT t.name FROM a.tags t))
                    AND (:author IS NULL OR a.author.username = :author)
                    AND (:favorited IS NULL OR :favorited IN (SELECT f.username FROM a.favorites f))
                    ORDER BY a.createdAt DESC
                    """)
    Page<Article> findArticlesByFacets(
            @Param("tag") String tag,
            @Param("author") String author,
            @Param("favorited") String favorited,
            Pageable pageable);
}