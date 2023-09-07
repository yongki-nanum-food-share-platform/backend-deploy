package yongkinanum.backenddeploy.review;

import lombok.Getter;

public class ReviewRequest {
    @Getter
    public static class WriteDTO {
        private Long idx;
        private String content;
        private Float starPoint;
    }

    @Getter
    public static class UpdateDTO {
        private Long idx;
        private String content;
    }
}
