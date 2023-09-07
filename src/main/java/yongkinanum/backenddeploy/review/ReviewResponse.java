package yongkinanum.backenddeploy.review;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewResponse {
    @Getter
    public static class FindAllDTO {
        private List<ReviewDTO> reviews;

        public FindAllDTO(List<Review> reviews) {
            this.reviews = reviews.stream()
                    .map(ReviewDTO::new)
                    .collect(Collectors.toList());
        }

        @Getter
        public static class ReviewDTO {
            private Long idx;
            private String content;
            private Float starPoint;
            private String shopName;
            private String orderName;
            private String createAt;

            public ReviewDTO(Review review) {
                this.idx = review.getIdx();
                this.content = review.getContent();
                this.starPoint = review.getStarPoint();
                this.shopName = review.getShop().getShopName();
                this.orderName = review.getOrder().getOrderName();
                this.createAt = review.getCreateAt().toString();
            }
        }
    }
}
