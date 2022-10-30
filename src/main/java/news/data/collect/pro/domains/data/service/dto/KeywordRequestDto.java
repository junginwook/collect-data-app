package news.data.collect.pro.domains.data.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.enums.NewsSite;
import news.data.collect.pro.commons.oauth.UserPrincipal;
import news.data.collect.pro.domains.data.controller.request.KeywordRequest;

import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeywordRequestDto {

    private EnumSet<NewsSite> newsSites;

    private List<String> keywords;

    private Long userId;

    public static KeywordRequestDto of(KeywordRequest request, UserPrincipal userPrincipal) {
        return new KeywordRequestDto(
                request.getNewsSites(),
                request.getKeywords(),
                userPrincipal.getId()
        );
    }
}
