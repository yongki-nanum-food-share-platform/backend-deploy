package yongkinanum.backenddeploy.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

class PostRequest {
    @Getter
    @Setter
    public static class WriteDTO {
        private String title;
        private String content;
        private List<OptionDTO> options;
        private String time;
        private String place;
        private String people;
        private Long idx;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .time(time)
                    .place(place)
                    .people(people)
                    .createAt(new Date())
                    .delete('N')
                    .build();
        }

        @Getter
        @Setter
        public static class OptionDTO {
            private Long idx;
            private int quantity;
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private String time;

        @NotBlank
        private String place;

        @NotBlank
        private String people;
    }
}
