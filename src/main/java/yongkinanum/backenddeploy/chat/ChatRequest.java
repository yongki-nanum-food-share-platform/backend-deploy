package yongkinanum.backenddeploy.chat;

import lombok.Getter;
import lombok.Setter;

public class ChatRequest {
    @Getter
    @Setter
    public static class EnterDTO {
        private Long idx;
    }
}
