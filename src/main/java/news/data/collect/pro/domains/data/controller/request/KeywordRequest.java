package news.data.collect.pro.domains.data.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import news.data.collect.pro.commons.enums.NewsSite;

import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;
import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class KeywordRequest {

    @NotEmpty
    private final EnumSet<NewsSite> newsSites;

    @NotEmpty
    private final List<String> keywords;
}
