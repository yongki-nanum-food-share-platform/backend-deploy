package yongkinanum.backenddeploy.shop;

import lombok.Getter;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.order.OrderResponse;
import yongkinanum.backenddeploy.order.item.Item;
import yongkinanum.backenddeploy.review.Review;
import yongkinanum.backenddeploy.shop.brand.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShopResponse {
    @Getter
    public static class FindAllDTO {
        List<ShopDTO> shops;

        public FindAllDTO(List<Shop> shops) {
            this.shops = shops.stream()
                    .map(ShopDTO::new)
                    .collect(Collectors.toList());
        }

        @Getter
        public static class ShopDTO {
            private Long idx;
            private String shopName;
            private String description;
            private Float starCount;

            public ShopDTO(Shop shop) {
                this.idx = shop.getIdx();
                this.shopName = shop.getShopName();
                this.description = shop.getDescription();
                this.starCount = shop.getStarPoint();
            }
        }
    }

    @Getter
    public static class FindDTO {
        private Long idx;
        private String shopName;
        private String shopAddress;
        private String shopDescription;
        private String image;
        private Float starPoint;
        private Integer orderCount;
        private Integer reviewCount;
        private List<MenuDTO> menus;
        private List<ReviewDTO> reviews;

        public FindDTO(List<Review> reviews, List<Option> options, Shop shop) {
            this.idx = shop.getIdx();
            this.shopName = shop.getShopName();
            this.shopAddress = shop.getShopAddress();
            this.shopDescription = shop.getDescription();
            this.image = shop.getBrand().getImage();
            this.starPoint = shop.getStarPoint();
            this.orderCount = shop.getOrderCount();
            this.reviewCount = shop.getReviewCount();

            Map<String, List<Option>> OptionsByMenu = options.stream()
                    .collect(Collectors.groupingBy(option -> option.getMenu().getMenuName()));

            this.menus = OptionsByMenu.entrySet().stream()
                    .map(entry -> new ShopResponse.FindDTO.MenuDTO(entry.getValue()))
                    .collect(Collectors.toList());

            this.reviews = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
        }

        @Getter
        public static class MenuDTO {
            private Long idx;
            private String menuName;
            private List<OptionDTO> options;

            public MenuDTO(List<Option> options) {
                this.idx = options.get(0).getMenu().getIdx();
                this.menuName = options.get(0).getMenu().getMenuName();
                this.options = options.stream()
                        .map(OptionDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public static class OptionDTO {
                private Long idx;
                private String optionName;
                private int price;

                public OptionDTO(Option option) {
                    this.idx = option.getIdx();
                    this.optionName = option.getOptionName();
                    this.price = option.getPrice();
                }
            }
        }

        @Getter
        public static class ReviewDTO {
            private Long idx;
            private String content;
            private Float starPoint;
            private String userName;
            private String createAt;

            public ReviewDTO(Review review) {
                this.idx = review.getIdx();
                this.content = review.getContent();
                this.starPoint = review.getStarPoint();
                this.userName = review.getUser().getUserName();
                this.createAt = review.getCreateAt().toString();
            }
        }
    }

    @Getter
    public static class FindBrandDTO {
        private Long idx;
        private String brandName;
        private String image;
        private int shopCount;

        public FindBrandDTO(Brand brand, int shopCount) {
            this.idx = brand.getIdx();
            this.brandName = brand.getBrandName();
            this.image = brand.getImage();
            this.shopCount = shopCount;
        }
    }
}
