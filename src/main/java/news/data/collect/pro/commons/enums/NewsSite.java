package news.data.collect.pro.commons.enums;

import news.data.collect.pro.domains.data.service.dto.DaumApiResponseDto;
import news.data.collect.pro.domains.data.service.dto.NaverApiResponseDto;
import news.data.collect.pro.domains.data.service.dto.NewsDataDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum NewsSite {
    daum("https://dapi.kakao.com/v2/search/web") {
        @Override
        public List<NewsDataDto> getNews(String keyword) {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK 90e7e9946db4a45518677eb9d16d650e");

            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();

            String targetUrl = url + "?query=" + keyword;

            HttpEntity<DaumApiResponseDto> response =
                    restTemplate.exchange(targetUrl, HttpMethod.POST, entity, DaumApiResponseDto.class);


            DaumApiResponseDto dto = response.getBody();

            List<NewsDataDto> newsDataDtoList = dto.getDocuments().stream()
                    .map(document -> new NewsDataDto(document.title, document.url, document.contents)).collect(Collectors.toList());

            return newsDataDtoList;
        }
    },
    naver("https://openapi.naver.com/v1/search/webkr.json") {
        @Override
        public List<NewsDataDto> getNews(String keyword) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", "UNfWYf2vyWgQt72xDaLZ");
            headers.set("X-Naver-Client-Secret", "cHBu5ukx6m");

            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();

            String targetUrl = url + "?query=" + keyword;

            HttpEntity<NaverApiResponseDto> response =
                    restTemplate.exchange(targetUrl, HttpMethod.GET, entity, NaverApiResponseDto.class);


            NaverApiResponseDto dto = response.getBody();

            List<NewsDataDto> newsDataDtoList = dto.getItems().stream()
                    .map(item -> new NewsDataDto(item.title, item.link, item.description)).collect(Collectors.toList());

            return newsDataDtoList;
        }
    };

    public String url;

    NewsSite(String url) {
        this.url = url;
    }

    public abstract List<NewsDataDto> getNews(
            String keyword
            );


}
