package yongkinanum.backenddeploy.chat;

import lombok.Getter;
import yongkinanum.backenddeploy.chat.message.Message;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatResponse {
    @Getter
    public static class FindAllDTO {
        private List<ChatDTO> chats;

        public FindAllDTO(List<Chat> chats, Map<Long, String> lastMsgs) {
            this.chats = chats.stream()
                    .map(chat -> new ChatDTO(chat, lastMsgs))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class ChatDTO {
            private Long idx;
            private String chatTitle;
            private String lastMsg;

            public ChatDTO(Chat chat, Map<Long, String> lastMsgs) {
                this.idx = chat.getIdx();
                this.chatTitle = chat.getTitle();
                this.lastMsg = lastMsgs.get(chat.getIdx());
            }
        }
    }

    @Getter
    public static class FindDTO {
        private Long idx;
        private List<MessageDTO> messages;

        public FindDTO(List<Message> messages) {
            this.messages = messages.stream()
                    .map(MessageDTO::new)
                    .collect(Collectors.toList());
        }

        @Getter
        public static class MessageDTO {
            private Long idx;
            private String userName;
            private String content;

            public MessageDTO(Message message) {
                this.idx = message.getIdx();
                this.userName = message.getUser().getUserName();
                this.content = message.getContent();
            }
        }
    }
}
