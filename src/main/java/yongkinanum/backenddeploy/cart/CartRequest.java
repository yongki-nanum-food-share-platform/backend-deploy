package yongkinanum.backenddeploy.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

class CartRequest {
    @Getter
    public static class AddDTO {
        private Long idx;
        private int quantity;
        private String shopName;
    }

    @Getter
    public static class UpdateDTO {
        @NotNull
        private Long idx;
        @NotNull
        private int quantity;
    }
}
