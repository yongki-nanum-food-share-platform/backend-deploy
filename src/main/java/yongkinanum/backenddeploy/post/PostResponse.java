package yongkinanum.backenddeploy.post;

import lombok.Getter;
import yongkinanum.backenddeploy.post.share.Share;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.user.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {
    @Getter
    public static class FindAllDTO {
        List<PostDTO> posts;

        public FindAllDTO(List<Post> posts, List<Share> shares) {
            this.posts = posts.stream()
                    .map(post -> new PostDTO(post, shares))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class PostDTO {
            private Long idx;
            private String image;
            private String title;
            private String time;
            private String place;
            private String menuName;
            private String people;

            public PostDTO(Post post, List<Share> shares) {
                this.idx = post.getIdx();
                this.image = post.getShop().getBrand().getImage();
                this.title = post.getTitle();
                this.time = post.getTime();
                this.place = post.getPlace();
                this.menuName = getSelectMenuName(post, shares);
                this.people = post.getPeople();
            }

            private String getSelectMenuName(Post post, List<Share> shares) {
                List<Share> findShares = shares.stream()
                        .filter(share -> share.getPost().getIdx() == post.getIdx())
                        .collect(Collectors.toList());

                return String.format("%s 외 %d건", findShares.get(0).getOption().getMenu().getMenuName(), findShares.size() - 1);
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
        private String menuName;
        private List<ShareDTO> shares;
        private Long shopIdx;
        private String shopName;
        private String image;
        private int tip;

        public FindDTO(Post post, List<Share> shares) {
            this.idx = post.getIdx();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.author = post.getUser().getUserName();
            this.createAt = post.getCreateAt().toString();
            this.time = post.getTime();
            this.place = post.getPlace();
            this.people = post.getPeople();
            this.menuName = shares.get(0).getOption().getMenu().getMenuName();
            this.shares = shares.stream()
                    .map(ShareDTO::new)
                    .collect(Collectors.toList());
            this.shopIdx = post.getShop().getIdx();
            this.shopName = post.getShop().getShopName();
            this.image = post.getShop().getBrand().getImage();
            this.tip = post.getShop().getTip();
        }

        @Getter
        public static class ShareDTO {
            private Long idx;
            private int quantity;
            private String optionName;

            public ShareDTO(Share share) {
                this.idx = share.getIdx();
                this.quantity = share.getQuantity();
                this.optionName = share.getOption().getOptionName();
            }
        }
    }

    @Getter
    public static class FindSpecificDTO {
        List<PostDTO> posts;

        public FindSpecificDTO(List<Post> posts, List<Share> shares) {
            this.posts = posts.stream()
                    .map(post -> new PostDTO(post, shares))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class PostDTO {
            private Long idx;
            private String image;
            private String title;
            private String time;
            private String place;
            private String menuName;
            private String people;

            public PostDTO(Post post, List<Share> shares) {
                this.idx = post.getIdx();
                this.image = post.getShop().getBrand().getImage();
                this.title = post.getTitle();
                this.time = post.getTime();
                this.place = post.getPlace();
                this.menuName = getSelectMenuName(post, shares);
                this.people = post.getPeople();
            }

            private String getSelectMenuName(Post post, List<Share> shares) {
                List<Share> findShares = shares.stream()
                        .filter(share -> share.getPost().getIdx() == post.getIdx())
                        .collect(Collectors.toList());

                return String.format("%s 외 %d건", findShares.get(0).getOption().getMenu().getMenuName(), findShares.size() - 1);
            }
        }
    }
}
