package news.data.collect.pro.domains.data.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DaumApiResponseDto {

    public Meta meta;

    public List<Document> documents;

    public static class Meta {
        private int total_count;
        private int pageable_count;
        private boolean is_end;
    }

    public static class Document {
        public String datetime;
        public String contents;
        public String title;
        public String url;
    }

}
