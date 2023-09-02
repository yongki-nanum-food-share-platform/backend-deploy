package yongkinanum.backenddeploy.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception400;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.Menu;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.menu.option.OptionJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final UserJPARepository userJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ShopJPARepository shopJPARepository;

    @Transactional
    public void addCarts(List<CartRequest.AddDTO> addDTOs, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        Shop findShop = shopJPARepository.findByShopName(addDTOs.get(0).getShopName());

        addCartException(addDTOs);

        //이미 유저의 장바구니에 있느 옵션의 아이디들을 Set에 담음
        List<Cart> carts = cartJPARepository.findAllByUserIdx(findUser.getIdx());
        Set<Long> cartRepositorySet = carts.stream()
                .map(cart -> cart.getOption().getIdx())
                .collect(Collectors.toSet());

        //옵션이 자체가 존재하는지와,
        //1. 유저의 장바구니에 존재하는 옵션은 개수를 업데이트
        //2. 유저의 장바구니에 존재하지 않는다면 장바구니에 추가
        for(CartRequest.AddDTO addDTO : addDTOs) {
            Long optionIdx = addDTO.getIdx();
            int quantity = addDTO.getQuantity();

            Option findOption = optionJPARepository.findById(optionIdx).orElseThrow(
                    () -> new Exception404("해당 옵션을 찾을 수 없습니다.")
            );

            // 해당 옵션이 이미 장바구니에 있을 때
            if (cartRepositorySet.contains(optionIdx)) {
                updateCartQuantity(carts, optionIdx, quantity);
            } else {    //없으면 장바구니에 담기
                Cart cart = Cart.builder()
                        .user(findUser)
                        .option(findOption)
                        .quantity(quantity)
                        .shop(findShop)
                        .build();

                cartJPARepository.save(cart);
            }
        }
    }
    private void updateCartQuantity(List<Cart> carts, Long optionIdx, int quantity) {
        for (Cart cart : carts) {
            if (cart.getOption().getIdx().equals(optionIdx)) {
                int newQuantity = cart.getQuantity() + quantity;
                cart.update(newQuantity);
            }
        }
    }

    private void addCartException(List<CartRequest.AddDTO> addDTOs) {
        Set<Long> cartSet = new HashSet<>();

        //동일한 옵션을 동시에 여러번 요청할 때 예외
        //예를들면 옵션 1번을 한 DTO에 두번 요청 시 예외 처리
        for (CartRequest.AddDTO addDTO : addDTOs) {
            Long optionIdx = addDTO.getIdx();
            if (cartSet.contains(optionIdx)) {
                throw new Exception400("동일한 옵션을 여러번 요청할 수 없습니다.");
            }

            cartSet.add(optionIdx);
        }

        /*//동일한 상품에 대한 요청이 아닐 시
        //예를들면 옵션 1번과 옵션 44번은 다른 상품이므로 장바구니 담기라는 기능에는 적합하지 않은 논리적인 모순
        List<Long> optionIdxs = addDTOs.stream()
                .map(addDTO -> addDTO.getIdx())
                .collect(Collectors.toList());

        Option option = optionJPARepository.findById(optionIdxs.get(0)).orElseThrow(
                () -> new Exception400("해당 옵션을 찾을 수 없습니다.")
        );

        Menu menu = option.getMenu();

        for(Long optionIdx : optionIdxs) {
            Option findOption = optionJPARepository.findById(optionIdx)
                    .orElseThrow(() -> new Exception400("해당 옵션을 찾을 수 업습니다."));
            Menu findMenu = findOption.getMenu();
            if(findMenu != menu) {
                throw new Exception400("다른 상품은 장바구니에 담을 수 없습니다.");
            }
        }*/
    }

    public CartResponse.FindDTO findCarts(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Cart> carts = cartJPARepository.findAllByUserIdx(findUser.getIdx());

        return new CartResponse.FindDTO(carts);
    }

    @Transactional
    public void updateCarts(List<CartRequest.UpdateDTO> updateDTOs, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Cart> carts = cartJPARepository.findAllByUserIdx(findUser.getIdx());

        updateCartException(carts, updateDTOs);

        for (Cart cart : carts) {
            for (CartRequest.UpdateDTO updateDTO : updateDTOs) {
                if (cart.getIdx().equals(updateDTO.getIdx())) {
                    cart.update(updateDTO.getQuantity());
                }
            }
        }
    }

    private void updateCartException(List<Cart> carts, List<CartRequest.UpdateDTO> requestDTOs) {
        if(carts.isEmpty()) {
            throw new Exception400("장바구니가 비어 있습니다.");
        }

        Set<Long> cartRepositorySet = carts.stream()
                .map(Cart::getIdx)
                .collect(Collectors.toSet());

        Set<Long> cartSet = new HashSet<>();

        requestDTOs.forEach(updateDTO -> {
            if (cartSet.contains(updateDTO.getIdx())) {  //같은 장바구니 ID로 여러번 요청
                throw new Exception400("해당 요청은 잘못 되었습니다.");
            }
            cartSet.add(updateDTO.getIdx());

            if (!cartRepositorySet.contains(updateDTO.getIdx())) {   //장바구니에 없는 ID의 변경 요청
                throw new Exception400("해당 상품은 장바구니에 존재하지 않습니다.");
            }
        });
    }

    @Transactional
    public void clearCarts(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        cartJPARepository.deleteByUserIdx(findUser.getIdx());
    }
}