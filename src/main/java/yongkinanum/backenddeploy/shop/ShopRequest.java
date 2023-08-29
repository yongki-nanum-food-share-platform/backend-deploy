package yongkinanum.backenddeploy.shop;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

class ShopRequest {
    @Getter
    public static class RegistDTO {
        @NotBlank
        private String shopName;

        @NotBlank
        private String shopAddress;

        @NotBlank
        private String tip;

        @NotBlank
        private String brandName;

        public Shop toEntity() {
            return Shop.builder()
                    .shopName(shopName)
                    .shopAddress(shopAddress)
                    .tip(Integer.parseInt(tip))
                    .build();
        }
    }

    @Getter
    public static class UpdateDTO {
        private String idx;
        private String newName;
        private String newAddress;
        private String newTip;
    }

    @Getter
    public static class UnregistDTO {
        private String idx;
    }
}
