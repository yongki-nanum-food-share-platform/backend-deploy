package yongkinanum.backenddeploy.review;

import lombok.Getter;
import lombok.Setter;

public class ReviewRequest {
    @Getter
    @Setter
    public static class WriteDTO {
        private Long idx;
        private String content;
        private Float starPoint;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String content;
    }
}
