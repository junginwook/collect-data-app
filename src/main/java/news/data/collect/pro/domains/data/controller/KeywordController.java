package news.data.collect.pro.domains.data.controller;

import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.argument.AuthUser;
import news.data.collect.pro.commons.http.ApiResponse;
import news.data.collect.pro.commons.oauth.UserPrincipal;
import news.data.collect.pro.domains.data.controller.request.KeywordRequest;
import news.data.collect.pro.domains.data.service.KeywordService;
import news.data.collect.pro.domains.data.service.dto.KeywordRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KeywordController {

    private final KeywordService keywordService;

    @PostMapping("/keyword")
    public ResponseEntity createData(
            @AuthUser UserPrincipal userPrincipal,
            @Valid @RequestBody KeywordRequest request) {


        keywordService.saveKeyword(KeywordRequestDto.of(request, userPrincipal));

        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("success")
                .status(200)
                .build();

        return ResponseEntity
                .status(200)
                .body(apiResponse);
    }


}
