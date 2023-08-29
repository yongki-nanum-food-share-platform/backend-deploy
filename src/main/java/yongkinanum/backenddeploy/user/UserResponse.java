package yongkinanum.backenddeploy.user;

import lombok.Getter;
import yongkinanum.backenddeploy.post.Post;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponse {
    @Getter
    public static class FindDTO {
        private Long idx;
        private String userId;
        private String userName;
        private String role;
        private List<PostDTO> posts;

        public FindDTO(List<Post> posts, User user) {
            this.idx = user.getIdx();
            this.userId = user.getUserId();
            this.userName = user.getUserName();
            this.role = user.getRole();
            this.posts = posts.stream()
                    .map(PostDTO::new)
                    .collect(Collectors.toList());
        }

        @Getter
        public static class PostDTO {
            private Long idx;
            private String title;
            private String createAt;

            public PostDTO(Post post) {
                this.idx = post.getIdx();
                this.title = post.getTitle();
                this.createAt = post.getCreateAt().toString();
            }
        }
    }
}
