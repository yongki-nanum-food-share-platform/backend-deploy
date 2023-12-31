package yongkinanum.backenddeploy.order;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.cart.Cart;
import yongkinanum.backenddeploy.cart.CartJPARepository;
import yongkinanum.backenddeploy.chat.Chat;
import yongkinanum.backenddeploy.chat.ChatJPARepository;
import yongkinanum.backenddeploy.chat.member.Member;
import yongkinanum.backenddeploy.chat.member.MemberJPARepository;
import yongkinanum.backenddeploy.core.error.exception.Exception400;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.order.delivery.Delivery;
import yongkinanum.backenddeploy.order.delivery.DeliveryJPARepository;
import yongkinanum.backenddeploy.order.item.Item;
import yongkinanum.backenddeploy.order.item.ItemJPARepository;
import yongkinanum.backenddeploy.post.Post;
import yongkinanum.backenddeploy.post.PostJPARepository;
import yongkinanum.backenddeploy.post.share.Share;
import yongkinanum.backenddeploy.post.share.ShareJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final UserJPARepository userJPARepository;
    private final ShopJPARepository shopJPARepository;
    private final CartJPARepository cartJPARepository;
    private final DeliveryJPARepository deliveryJPARepository;
    private final PostJPARepository postJPARepository;
    private final ShareJPARepository shareJPARepository;
    private final ChatJPARepository chatJPARepository;
    private final MemberJPARepository memberJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        User findUser  = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Cart> carts = cartJPARepository.findAllByUserIdx(findUser.getIdx());

        if(carts.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        String orderName = String.format("%s 외 %d건",carts.get(0).getOption().getMenu().getMenuName(), carts.size() - 1);

        Order newOrder = Order.builder()
                .user(findUser)
                .orderName(orderName)
                .cancel('N')
                .build();

        // 수정 필요
        //////////////////////////////////////
        Delivery newDelivery = Delivery.builder()
                .address("연산동")
                .status('N')
                .order(newOrder)
                .user(findUser)
                .build();
        /////////////////////////////////////

        List<Item> items = makeOrderItems(carts, newOrder);

        //주문 영속화
        orderJPARepository.save(newOrder);

        //가게 주문 수 증가
        Set<Long> shopsIdx = new HashSet<>();

        for(Cart cart : carts) {
            Long findShopIdx = cart.getShop().getIdx();
            if(shopsIdx.contains(findShopIdx)) {
                continue;
            }

            shopsIdx.add(findShopIdx);

            Shop findShop = shopJPARepository.findById(findShopIdx).orElseThrow(
                    () -> new Exception404("해당 가게를 찾을 수 없습니다.")
            );

            findShop.setOrderCount(findShop.getOrderCount() + 1);
        }

        //장바구니 비우기
        cartJPARepository.deleteByUserIdx(findUser.getIdx());

        //주문 아이템 모두 저장
        itemJPARepository.saveAll(items);

        //배달 시작
        deliveryJPARepository.save(newDelivery);

        return new OrderResponse.SaveDTO(newOrder);
    }

    private List<Item> makeOrderItems(List<Cart> carts, Order newOrder) {
        return carts.stream()
                .map(cart -> Item.builder()
                        .quantity(cart.getQuantity())
                        .price(cart.getOption().getPrice() * cart.getQuantity())
                        .createAt(new Date())
                        .order(newOrder)
                        .option(cart.getOption())
                        .shop(cart.getShop())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderResponse.InfoDTO findOrderInfo(OrderRequest.InfoDTO infoDTO) {
        Post findPost = postJPARepository.findById(infoDTO.getPostIdx()).orElseThrow(
                () -> new Exception404("해당 게시물을 찾을 수 없습니다."));

        List<Share> findShares = shareJPARepository.findAllShareByPostIdx(findPost.getIdx());

        List<Option> findOptions = findShares.stream()
                .map(findShare -> findShare.getOption())
                .collect(Collectors.toList());

        Shop findShop = findPost.getShop();

        Chat findChat = chatJPARepository.findChatByPostIdx(findPost.getIdx());
        List<String> findMemberNames = memberJPARepository.findMemberByChatIdx(findChat.getIdx()).stream()
                .map(findMember -> findMember.getUser().getUserName())
                .collect(Collectors.toList());

        return new OrderResponse.InfoDTO(findPost.getIdx(), findShop, findOptions, findMemberNames);
    }

    public OrderResponse.FindAllDTO findAllOrders(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Order> orders = orderJPARepository.findAllOrderByUserIdx(findUser.getIdx());

        List<Item> items = itemJPARepository.findAllItemByUserIdx(findUser.getIdx());

        List<Delivery> deliveries = new ArrayList<>();

        for(Order order : orders) {
            Delivery delivery = deliveryJPARepository.findByOrderIdx(order.getIdx());
            deliveries.add(delivery);
        }

        return new OrderResponse.FindAllDTO(orders, items, deliveries);
    }

    public OrderResponse.FindDTO findOrder(Long idx, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        Order findOrder = orderJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다.")
        );

        List<Item> items = itemJPARepository.findAllItemByOrderIdx(findOrder.getIdx());

        Delivery delivery = deliveryJPARepository.findByOrderIdx(findOrder.getIdx());

        return new OrderResponse.FindDTO(findOrder, items, delivery);
    }

    public OrderResponse.FindCancelDTO findCancelOrder(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Order> orders = orderJPARepository.findCancelOrderByUserIdx(findUser.getIdx());

        List<Item> items = itemJPARepository.findAllItemByUserIdx(findUser.getIdx());

        return new OrderResponse.FindCancelDTO(orders, items);
    }

    @Transactional
    public void cancelOrder(Long idx, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        Order findOrder = orderJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다.")
        );

        if(findOrder.getUser() != findUser) {
            throw new Exception403("권한이 없습니다.");
        }

        findOrder.setCancel('Y');
    }
}