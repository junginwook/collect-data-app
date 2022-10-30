package news.data.collect.pro.entity.common;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime regDt = LocalDateTime.now();

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime modDt = LocalDateTime.now();

    @Column(updatable = false)
    LocalDateTime delDt = null;
}
