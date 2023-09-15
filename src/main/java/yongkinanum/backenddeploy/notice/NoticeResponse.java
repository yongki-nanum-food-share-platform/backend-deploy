package yongkinanum.backenddeploy.notice;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class NoticeResponse {
    @Getter
    public static class FindAllDTO {
        private List<NoticeDTO> notices;

        public FindAllDTO(List<Notice> notices) {
            this.notices = notices.stream()
                    .map(NoticeDTO::new)
                    .collect(Collectors.toList());
        }

        @Getter
        public static class NoticeDTO {
            private String content;
            private String createAt;

            public NoticeDTO(Notice notice) {
                this.content = notice.getContent();
                this.createAt = notice.getCreateAt().toString();
            }
        }
    }
}
