package news.data.collect.pro.commons.oauth;

import news.data.collect.pro.commons.enums.AuthProvider;
import news.data.collect.pro.commons.oauth.oauthUserInfo.GoogleUserInfo;
import news.data.collect.pro.commons.oauth.oauthUserInfo.KakaoUserInfo;
import news.data.collect.pro.commons.oauth.oauthUserInfo.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        switch (AuthProvider.valueOf(registrationId)) {
            case kakao: return new KakaoUserInfo(attributes);
            case google: return new GoogleUserInfo(attributes);
        }
        return null;
    }
}
