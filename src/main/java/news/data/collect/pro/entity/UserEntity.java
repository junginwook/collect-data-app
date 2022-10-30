package news.data.collect.pro.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.data.collect.pro.commons.enums.AuthProvider;
import news.data.collect.pro.entity.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String username;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeywordEntity> keywordEntityList;

    public UserEntity(String username, String email, AuthProvider provider) {

        this.username = username;
        this.email = email;
        this.provider = provider;
    }

}
