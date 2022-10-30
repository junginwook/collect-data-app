package news.data.collect.pro.domains.data.repository;

import news.data.collect.pro.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
