package yongkinanum.backenddeploy.shop;

import lombok.Getter;
import lombok.Setter;
import yongkinanum.backenddeploy.shop.brand.Brand;
import yongkinanum.backenddeploy.user.User;

import javax.validation.constraints.NotBlank;

class ShopRequest {
    @Getter
    @Setter
    public static class RegistDTO {
        @NotBlank
        private String shopName;

        @NotBlank
        private String shopAddress;

        @NotBlank
        private String tip;

        @NotBlank
        private String brandName;

        public Shop toEntity(User user, Brand brand) {
            return Shop.builder()
                    .shopName(shopName)
                    .shopAddress(shopAddress)
                    .starPoint(0.0F)
                    .reviewCount(0)
                    .orderCount(0)
                    .unregist('N')
                    .tip(Integer.parseInt(tip))
                    .user(user)
                    .brand(brand)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        @NotBlank
        private Long idx;
        private String newName;
        private String newAddress;
        private String newTip;
    }

    @Getter
    @Setter
    public static class UnregistDTO {
        private Long idx;
    }
}
