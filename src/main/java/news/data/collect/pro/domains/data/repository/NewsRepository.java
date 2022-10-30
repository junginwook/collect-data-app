package news.data.collect.pro.domains.data.repository;

import news.data.collect.pro.entity.KeywordEntity;
import news.data.collect.pro.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

}
