package yongkinanum.backenddeploy.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_USER("일반회원"),
    ROLE_SHOPPER("가게사장님"),
    ROLE_DELIVERYMAN("라이더");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
