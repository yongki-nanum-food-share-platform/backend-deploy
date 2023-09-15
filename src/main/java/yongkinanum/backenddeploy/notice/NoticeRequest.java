package yongkinanum.backenddeploy.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NoticeRequest {
    @Getter
    @Setter
    public static class WriteDTO {
        private String content;
    }
}
