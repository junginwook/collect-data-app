package news.data.collect.pro.commons.oauth.oauthUserInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo extends OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getUserName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
