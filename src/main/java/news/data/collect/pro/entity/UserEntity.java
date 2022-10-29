package news.data.collect.pro.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.data.collect.pro.commons.enums.AuthProvider;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String username;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    public UserEntity(String username, String email, AuthProvider provider) {

        this.username = username;
        this.email = email;
        this.provider = provider;
    }

}
