package news.data.collect.pro.entity;

import com.sun.tools.javac.comp.MemberEnter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.data.collect.pro.commons.enums.AuthProvider;
import news.data.collect.pro.commons.enums.NewsSite;
import news.data.collect.pro.entity.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class KeywordEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String keyword;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsSite newsSite;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "keywordEntity",
    cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsEntity> newsEntityList;

    //키워드 생성자
    public KeywordEntity(String keyword, NewsSite newsSite) {

        this.keyword = keyword;
        this.newsSite = newsSite;
    }

    public void setData(List<NewsEntity> newsEntityList, UserEntity userEntity) {
        this.newsEntityList = newsEntityList;
        //뉴스 데이터 추가
        for (NewsEntity newsEntity: newsEntityList) {
            newsEntity.setKeywordEntity(this);
        }
        //유저 데이터 추가
        this.userEntity = userEntity;
        userEntity.getKeywordEntityList().add(this);
    }
}
