package yongkinanum.backenddeploy.post;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

class PostRequest {
    @Getter
    public static class WriteDTO {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private String option;

        @NotBlank
        private String time;

        @NotBlank
        private String place;

        @NotBlank
        private String people;

        @NotBlank
        private String shop;

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
    }

    @Getter
    public static class UpdateDTO {
        @NotBlank
        private String idx;

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

    @Getter
    public static class DeleteDTO {
        @NotBlank
        private String idx;
    }
}
