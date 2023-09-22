package yongkinanum.backenddeploy.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.chat.Chat;
import yongkinanum.backenddeploy.chat.ChatJPARepository;
import yongkinanum.backenddeploy.chat.member.Member;
import yongkinanum.backenddeploy.chat.member.MemberJPARepository;
import yongkinanum.backenddeploy.chat.message.Message;
import yongkinanum.backenddeploy.chat.message.MessageJPARepository;
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
import java.util.Date;
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
    private final ChatJPARepository chatJPARepository;
    private final MemberJPARepository memberJPARepository;
    private final MessageJPARepository messageJPARepository;

    @Transactional
    public void writePost(PostRequest.WriteDTO writeDTO, User user) {
        Shop findShop = shopJPARepository.findById(writeDTO.getIdx()).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );

        List<PostRequest.WriteDTO.OptionDTO> optionDTOs = writeDTO.getOptions();

        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        //게시물 저장
        Post post = writeDTO.toEntity();
        post.setShop(findShop);

        post.setUser(findUser);

        postJPARepository.save(post);

        //게시물에 포함될 공유 메뉴, 옵션 저장
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

        //게시물 작성 시, 생성될 채팅방 저장
        Chat chat = Chat.builder()
                .title("'" + post.getTitle() + "' 게시물의 채팅방")
                .createAt(new Date())
                .delete('N')
                .post(post)
                .build();

        chatJPARepository.save(chat);

        //채팅방 생성 시, 글 작성자는 채팅방 멤버로 간주하고 저장
        Member member = Member.builder()
                .user(findUser)
                .chat(chat)
                .build();

        memberJPARepository.save(member);

        //채팅방 생성 시, 노출될 기본 메시지
        Message defaultMessage = Message.builder()
                .content("아직 상대방과 주고 받은 대화가 없어요! 대화를 나눠보세요.")
                .createAt(new Date())
                .user(findUser)
                .chat(chat)
                .build();

        messageJPARepository.save(defaultMessage);
    }

    public PostResponse.FindAllDTO findAllPost() {
        List<Post> findPosts = postJPARepository.findAll();
        List<Share> findShares = shareJPARepository.findAll();

        return new PostResponse.FindAllDTO(findPosts, findShares);
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

    public PostResponse.FindSpecificDTO findSpecificPosts(PostRequest.FindSpecificDTO findSpecificDTO) {
        List<Post> findPosts = postJPARepository.findPostByTitle(findSpecificDTO.getTitle());
        List<Share> findShares = shareJPARepository.findAll();

        return new PostResponse.FindSpecificDTO(findPosts, findShares);
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
