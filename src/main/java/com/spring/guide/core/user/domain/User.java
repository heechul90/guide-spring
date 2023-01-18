package com.spring.guide.core.user.domain;

import com.spring.guide.core.user.dto.UpdateUserParam;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@SequenceGenerator(
        name = "user_seq_generator",
        sequenceName = "user_seq",
        initialValue = 1, allocationSize = 50
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;

    private String name;
    private String email;

    /**
     * user 생성
     */
    @Builder(builderMethodName = "createUser")
    public User(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    /**
     * user 수정
     */
    public void updateUser(UpdateUserParam param) {
        this.name = param.getName();
        this.email = param.getEmail();
    }
}
