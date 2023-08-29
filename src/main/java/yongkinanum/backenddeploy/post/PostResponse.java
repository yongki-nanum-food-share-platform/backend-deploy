package yongkinanum.backenddeploy.post;

import lombok.Getter;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.user.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {
    @Getter
    public static class FindAllDTO {
        List<PostDTO> posts;

        public FindAllDTO(List<Post> posts) {
            this.posts = posts.stream().map(PostDTO::new).collect(Collectors.toList());
        }

        @Getter
        public static class PostDTO {
            private Long idx;
            private String image;
            private String title;
            private String time;
            private String place;
            private String people;

            public PostDTO(Post post) {
                this.idx = post.getIdx();
                this.image = post.getShop().getBrand().getImage();
                this.title = post.getTitle();
                this.time = post.getTime();
                this.place = post.getPlace();
                this.people = post.getPeople();
            }
        }
    }

    @Getter
    public static class FindDTO {
        private Long idx;
        private String title;
        private String content;
        private String author;
        private String createAt;
        private String time;
        private String place;
        private String people;
        private String shopName;
        private String image;
        private int tip;

        public FindDTO(Post post) {
            this.idx = post.getIdx();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.author = post.getUser().getUserName();
            this.createAt = post.getCreateAt().toString();
            this.time = post.getTime();
            this.place = post.getPlace();
            this.people = post.getPeople();
            this.shopName = post.getShop().getShopName();
            this.image = post.getShop().getBrand().getImage();
            this.tip = post.getShop().getTip();
        }
    }
}
