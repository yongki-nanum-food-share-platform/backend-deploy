package yongkinanum.backenddeploy.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.menu.option.OptionJPARepository;
import yongkinanum.backenddeploy.post.share.Share;
import yongkinanum.backenddeploy.post.share.ShareJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostJPARepository postJPARepository;
    private final ShareJPARepository shareJPARepository;
    private final UserJPARepository userJPARepository;
    private final ShopJPARepository shopJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void writePost(PostRequest.WriteDTO writeDTO, User user) {
        Shop findShop = shopJPARepository.findById(writeDTO.getIdx()).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );

        List<PostRequest.WriteDTO.OptionDTO> optionDTOs = writeDTO.getOptions();

        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        Post post = writeDTO.toEntity();
        post.setShop(findShop);

        post.setUser(findUser);

        postJPARepository.save(post);

        List<Share> shares = new ArrayList<>();

        for(PostRequest.WriteDTO.OptionDTO optionDTO : optionDTOs) {
            Option findOption = optionJPARepository.findByOptionId(optionDTO.getIdx());
            Share newShare = Share.builder()
                    .post(post)
                    .quantity(optionDTO.getQuantity())
                    .option(findOption)
                    .build();

            shares.add(newShare);
        }

        shareJPARepository.saveAll(shares);
    }

    public PostResponse.FindAllDTO findAllPost() {
        List<Post> posts = postJPARepository.findAll();

        return new PostResponse.FindAllDTO(posts);
    }

    public PostResponse.FindDTO findPost(Long idx) {
        Post findPost = postJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 게시물을 찾을 수 없습니다.")
        );

        List<Share> findShares = shareJPARepository.findAllShareByPostIdx(findPost.getIdx());

        if(findPost.getDelete() == 'Y') {
            throw new Exception404("해당 게시물은 삭제되었습니다.");
        }

        return new PostResponse.FindDTO(findPost, findShares);
    }

    @Transactional
    public void updatePost(Long idx, PostRequest.UpdateDTO updateDTO, User user) {
        Post findPost = postJPARepository.findById(idx).orElseThrow(
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
