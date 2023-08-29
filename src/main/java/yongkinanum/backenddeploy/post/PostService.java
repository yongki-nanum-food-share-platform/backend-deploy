package yongkinanum.backenddeploy.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.menu.option.OptionJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;
import yongkinanum.backenddeploy.user.UserResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostJPARepository postJPARepository;
    private final UserJPARepository userJPARepository;
    private final ShopJPARepository shopJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void writePost(PostRequest.WriteDTO writeDTO, User user) {
        Shop findShop = shopJPARepository.findById(Long.parseLong(writeDTO.getShop())).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );

        Option findOption = optionJPARepository.findById(Long.parseLong(writeDTO.getOption())).orElseThrow(
                () -> new Exception404("해당 옵션을 찾을 수 없습니다.")
        );

        User authorUser = userJPARepository.findByUserId(user.getUserId());

        if(authorUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }

        Post post = writeDTO.toEntity();
        post.setShop(findShop);

        post.setUser(authorUser);
        post.setOption(findOption);

        postJPARepository.save(post);
    }

    public PostResponse.FindAllDTO findAllPost() {
        List<Post> posts = postJPARepository.findAll();

        return new PostResponse.FindAllDTO(posts);
    }

    public PostResponse.FindDTO findPost(Long idx) {
        Post findPost = postJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 게시물을 찾을 수 없습니다.")
        );

        if(findPost.getDelete() == 'Y') {
            throw new Exception404("해당 게시물은 삭제되었습니다.");
        }

        return new PostResponse.FindDTO(findPost);
    }

    @Transactional
    public void updatePost(PostRequest.UpdateDTO updateDTO, User user) {
        Post findPost = postJPARepository.findById(Long.parseLong(updateDTO.getIdx())).orElseThrow(
                () -> new Exception404("해당 게시물을 찾을 수 없습니다.")
        );

        User findUser = userJPARepository.findByUserId(user.getUserId());

        if(findPost.getUser() != findUser) {
            throw new Exception403("권한이 없습니다.");
        }

        findPost.setTitle(updateDTO.getTitle());
        findPost.setContent(updateDTO.getContent());
        findPost.setTime(updateDTO.getTime());
        findPost.setPlace(updateDTO.getPlace());
        findPost.setPeople(updateDTO.getPeople());
    }

    @Transactional
    public void deletePost(Long idx, User user) {
        Post findPost = postJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 게시물을 찾을 수 없습니다.")
        );

        User findUser = userJPARepository.findByUserId(user.getUserId());

        if(findPost.getUser() != findUser) {
            throw new Exception403("권한이 없습니다.");
        }

        findPost.setDelete('Y');
    }
}
