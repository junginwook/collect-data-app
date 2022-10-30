package news.data.collect.pro.domains.data.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverApiResponseDto {

    public String lastBuildDate;
    public int total;
    public int start;
    public int display;

    private List<Item> items;

    public static class Item {
        public String title;
        public String link;
        public String description;
    }

}
