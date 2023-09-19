package yongkinanum.backenddeploy.chat;

import lombok.Getter;
import lombok.Setter;

public class ChatRequest {
    @Getter
    public static class ReceiveDTO {
        private String userName;
        private String content;
    }

    @Getter
    @Setter
    public static class EnterDTO {
        private Long idx;
    }
}
