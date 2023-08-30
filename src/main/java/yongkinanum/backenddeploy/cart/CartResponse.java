package yongkinanum.backenddeploy.cart;

import lombok.Getter;
import yongkinanum.backenddeploy.menu.Menu;
import yongkinanum.backenddeploy.menu.option.Option;

import java.util.List;
import java.util.stream.Collectors;

class CartResponse {
    @Getter
    public static class FindDTO {
        List<MenuDTO> menus;
        private int totalPrice;

        public FindDTO(List<Cart> carts) {
            this.menus = carts.stream()
                    // 중복되는 상품 걸러내기
                    .map(cart -> cart.getOption().getMenu())
                    .distinct()
                    .map(menu -> new MenuDTO(menu, carts))
                    .collect(Collectors.toList());
            this.totalPrice = carts.stream()
                    .mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();
        }

        @Getter
        public class MenuDTO {
            private Long idx;
            private String menuName;
            private List<CartDTO> carts;

            public MenuDTO(Menu menu, List<Cart> carts) {
                this.idx = menu.getIdx();
                this.menuName = menu.getMenuName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.carts = carts.stream()
                        .filter(cart -> cart.getOption().getMenu().getIdx() == menu.getIdx())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public class CartDTO {
                private Long idx;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.idx = cart.getIdx();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                public class OptionDTO {
                    private Long idx;
                    private String optionName;
                    private int price;

                    public OptionDTO(Option option) {
                        this.idx = option.getIdx();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }
}
