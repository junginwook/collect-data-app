package news.data.collect.pro.commons.oauth.oauthUserInfo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo extends OAuth2UserInfo{

    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getUserName() {

        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return properties.get("nickname").toString();
    }

    @Override
    public String getEmail() {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return kakaoAccount.get("email").toString();
    }
}
