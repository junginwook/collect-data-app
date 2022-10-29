package news.data.collect.pro.commons.oauth;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import news.data.collect.pro.commons.utils.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * csrf 공격 방지를 위해 리소스 서버에서 전달 받은 state 매게 변수를 어플리케이션 서버에서 쿠키에 저장
 * oAuth2 공급자는 다시 state 값을 반환하여 초기의 state 값과 비교한다
 * HttpCookieOAuth2AuthorizationRequestRepository 는 초기의 state 매게 변수 값을 cookie에 저장하기 위해 필요한 클래스
 * 추가적으로 front 서버로 리다이렉트할 redirect_uri 도 쿠키에 저장한다.
 */
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    private static final int cookieExpireSeconds = 180;

    /**
     * cookie에 저장되어 있던 authorizationRequest 들을 가져온다
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    /**
     * 플랫폼으로 보내기 위한 Request를 'oauth2_auth_request'라는 cookie에 저장한다
     * 추가적으로 클라이언트로 리다이렉트할 redirect_uri 도 쿠키에 저장
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }

        //authorizationRequest 를 쿠키에 저장
        CookieUtils.addCookie(
                response,
                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest),
                cookieExpireSeconds
        );

        //redirect 파라미터를 쿠키에 넣어준다
        String redirectUriAfterLogin =  request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(
                    response,
                    REDIRECT_URI_PARAM_COOKIE_NAME,
                    redirectUriAfterLogin,
                    cookieExpireSeconds
            );
        }
    }

    /**
     * Remove 를 재정의 해서 cookie 를 가져오고 remove 는 successHandler 또는 failHandler 에서 할 수 있도록 한다
     * redirect uri 를 handler에서 사용해야 하므로
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return AuthorizationRequestRepository.super.removeAuthorizationRequest(request, response);
    }

    /**
     * 쿠키 데이터 삭제
     */
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }

}
