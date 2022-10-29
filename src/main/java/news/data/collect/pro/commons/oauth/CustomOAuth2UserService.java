package news.data.collect.pro.commons.oauth;

import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.enums.AuthProvider;
import news.data.collect.pro.commons.enums.ErrorCode;
import news.data.collect.pro.commons.exception.CustomException;
import news.data.collect.pro.commons.oauth.oauthUserInfo.OAuth2UserInfo;
import news.data.collect.pro.domains.auth.repository.AuthRepository;
import news.data.collect.pro.domains.auth.service.AuthService;
import news.data.collect.pro.entity.UserEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OAuth2UserInfo 에 Email 이 있는지 검사와, A라는 플랫폼으로 가입되어 있는데
 * B플랫폼으로 가입하려는 경우를 검사한다.
 * email이 존재하지 않는 경우 insert 하며, UserPrincipal을 반환한다
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AuthRepository authRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());


        Optional<UserEntity> userOptional = authRepository.findByEmail(oAuth2UserInfo.getEmail());

        UserEntity userEntity = null;

        //해당 이메일로 가입한 계정이 이미 있는 경우 공급자 비교
        if(userOptional.isPresent()) {

            userEntity = userOptional.get();

            //가져온 유저의 공급자명과 넘어온 공급자 명이 다른 경우
            if(userEntity.getProvider() != AuthProvider.valueOf(registrationId)) {
                //이미 다른 공급자가 존재하기 때문에 진행 불가
                throw new CustomException(ErrorCode.BAD_ARGUMENT);
            }

        }
        //해당 이메일로 가입한 계정이 없으므로 가입처리 한다
        else {

            userEntity = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(userEntity);
    }

    private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        UserEntity userEntity = new UserEntity(
                oAuth2UserInfo.getUserName(),
                oAuth2UserInfo.getEmail(),
                AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));

        return authRepository.save(
                userEntity
        );
    }

}
