package news.data.collect.pro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import news.data.collect.pro.entity.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class NewsEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String title;

    @Column(nullable = false)
    @Lob
    private String contents;

    @Column(nullable = false)
    @Lob
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    private KeywordEntity keywordEntity;

    public NewsEntity(String title, String contents, String link) {
        this.title = title;
        this.contents = contents;
        this.link = link;
    }

    public void setKeywordEntity(KeywordEntity keywordEntity) {
        this.keywordEntity = keywordEntity;
    }
}
