package news.data.collect.pro.domains.data.service;

import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.enums.ErrorCode;
import news.data.collect.pro.commons.enums.NewsSite;
import news.data.collect.pro.commons.exception.CustomException;
import news.data.collect.pro.domains.data.repository.KeywordRepository;
import news.data.collect.pro.domains.data.repository.NewsRepository;
import news.data.collect.pro.domains.data.repository.UserRepository;
import news.data.collect.pro.domains.data.service.dto.KeywordRequestDto;
import news.data.collect.pro.domains.data.service.dto.KeywordResponseDto;
import news.data.collect.pro.domains.data.service.dto.NewsDataDto;
import news.data.collect.pro.entity.KeywordEntity;
import news.data.collect.pro.entity.NewsEntity;
import news.data.collect.pro.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    private final UserRepository userRepository;

    @Transactional
    public void saveKeyword(KeywordRequestDto requestDto) {

        UserEntity userEntity = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        saveKeywords(requestDto.getKeywords(), requestDto.getNewsSites(), userEntity);
    }


    private void saveKeywords(List<String> keywords, Set<NewsSite> newsSites, UserEntity userEntity) {
        for (String keyword: keywords) {
            for (NewsSite newsSite: newsSites) {
                KeywordEntity keywordEntity = new KeywordEntity(keyword, newsSite);

                List<NewsEntity> newsEntityList = useNewsApi(newsSite, keyword);

                keywordEntity.setData(newsEntityList, userEntity);
                //키워드 저장
                keywordRepository.save(keywordEntity);
            }
        }
    }

    private List<NewsEntity> useNewsApi(NewsSite newsSite, String keyword) {

        List<NewsDataDto> newsList = newsSite.getNews(keyword);

        List<NewsEntity> newsEntityList = new ArrayList<>();

        for(NewsDataDto newsDataDto: newsList) {

            NewsEntity newsEntity = new NewsEntity(
                    newsDataDto.getTitle(),
                    newsDataDto.getContent(),
                    newsDataDto.getLink()
            );

            newsEntityList.add(newsEntity);
        }

        return newsEntityList;
    }

}
