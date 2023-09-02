package yongkinanum.backenddeploy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.cart.Cart;
import yongkinanum.backenddeploy.cart.CartJPARepository;
import yongkinanum.backenddeploy.core.error.exception.Exception400;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.order.delivery.Delivery;
import yongkinanum.backenddeploy.order.delivery.DeliveryJPARepository;
import yongkinanum.backenddeploy.order.item.Item;
import yongkinanum.backenddeploy.order.item.ItemJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final UserJPARepository userJPARepository;
    private final CartJPARepository cartJPARepository;
    private final DeliveryJPARepository deliveryJPARepository;

    @Transactional
    public void saveOrder(User user) {
        User findUser  = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Cart> carts = cartJPARepository.findAllByUserIdx(findUser.getIdx());

        if(carts.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Order newOrder = Order.builder()
                .user(findUser)
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

        //장바구니 비우기
        cartJPARepository.deleteByUserIdx(findUser.getIdx());

        //주문 아이템 모두 저장
        itemJPARepository.saveAll(items);

        //배달 시작
        deliveryJPARepository.save(newDelivery);
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

    public OrderResponse.FindAllDTO findAllOrders(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Order> orders = orderJPARepository.findAllOrderByUserIdx(findUser.getIdx());

        List<Item> items = itemJPARepository.findAllItemByUserIdx(findUser.getIdx());

        return new OrderResponse.FindAllDTO(orders, items);
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
