package news.data.collect.pro.domains.data.repository;

import news.data.collect.pro.entity.KeywordEntity;
import news.data.collect.pro.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {


    @Query("select k from KeywordEntity k " +
            "where k.keyword in :keywords")
    List<KeywordEntity> findKeyword(
            @Param("keywords") List<String> keywords
    );

}
