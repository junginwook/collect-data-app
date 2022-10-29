package news.data.collect.pro.commons.oauth;

import com.sun.tools.javac.comp.MemberEnter;
import lombok.RequiredArgsConstructor;
import news.data.collect.pro.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User {

    public final Long id;
    public final String email;
    public final String username;



    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return id.toString();
    }

    public static UserPrincipal create(UserEntity userEntity) {

        return new UserPrincipal(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername()
        );
    }
}
