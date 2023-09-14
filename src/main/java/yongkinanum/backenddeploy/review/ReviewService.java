package yongkinanum.backenddeploy.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.order.Order;
import yongkinanum.backenddeploy.order.OrderJPARepository;
import yongkinanum.backenddeploy.order.item.Item;
import yongkinanum.backenddeploy.order.item.ItemJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewJPARepository reviewJPARepository;
    private final UserJPARepository userJPARepository;
    private final ShopJPARepository shopJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public void writeReview(ReviewRequest.WriteDTO writeDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        findUser.findUserNullCheck(findUser);

        Order findOrder = orderJPARepository.findById(writeDTO.getIdx()).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다.")
        );

        List<Item> items = itemJPARepository.findAllItemByOrderIdx(findOrder.getIdx());

        Shop findShop = shopJPARepository.findById(items.get(0).getShop().getIdx()).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );

        Review review = Review.builder()
                .content(writeDTO.getContent())
                .starPoint(writeDTO.getStarPoint())
                .createAt(new Date())
                .delete('N')
                .user(findUser)
                .shop(findShop)
                .order(findOrder)
                .build();

        Integer newReviewCount = findShop.getReviewCount() + 1;

        Float oldStarPoint = findShop.getStarPoint() * findShop.getReviewCount();
        Float newStarPoint = (oldStarPoint + writeDTO.getStarPoint()) / (float) newReviewCount;

        //별점 갱신
        findShop.setStarPoint(newStarPoint);

        //리뷰 개수 갱신
        findShop.setReviewCount(newReviewCount);

        reviewJPARepository.save(review);
    }

    public ReviewResponse.FindAllDTO findAllReviews(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        findUser.findUserNullCheck(findUser);

        List<Review> reviews = reviewJPARepository.findByUserId(findUser.getIdx());

        return new ReviewResponse.FindAllDTO(reviews);
    }

    @Transactional
    public void updateReview(Long idx, ReviewRequest.UpdateDTO updateDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        findUser.findUserNullCheck(findUser);

        Review findReview = reviewJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 리뷰를 찾을 수 없습니다.")
        );

        if(findReview.getUser() != findUser) {
            throw new Exception403("권한이 없습니다.");
        }

        findReview.setContent(updateDTO.getContent());
    }

    @Transactional
    public void deleteReview(Long idx, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        findUser.findUserNullCheck(findUser);

        Review findReview = reviewJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 리뷰를 찾을 수 없습니다.")
        );

        if(findReview.getUser() != findUser) {
            throw new Exception403("권한이 없습니다.");
        }

        findReview.setDelete('Y');
    }
}
