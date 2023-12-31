package yongkinanum.backenddeploy.order;

import lombok.Getter;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.Menu;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.order.delivery.Delivery;
import yongkinanum.backenddeploy.order.item.Item;
import yongkinanum.backenddeploy.shop.Shop;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter
    public static class SaveDTO {
        public Long orderIdx;

        public SaveDTO(Order order) {
            this.orderIdx = order.getIdx();
        }
    }

    @Getter
    public static class InfoDTO {
        private Long postIdx;
        private String shopName;
        private String shopAddress;
        private List<MenuDTO> menus;
        private List<String> peoples;
        private int peopleCount;

        public InfoDTO(Long idx, Shop shop, List<Option> options, List<String> peoples) {
            this.postIdx = idx;
            this.shopName = shop.getShopName();
            this.shopAddress = shop.getShopAddress();

            Map<String, List<Option>> OptionsByMenu = options.stream()
                    .collect(Collectors.groupingBy(option ->option.getMenu().getMenuName()));

            this.menus = OptionsByMenu.entrySet().stream()
                    .map(entry -> new InfoDTO.MenuDTO(entry.getValue()))
                    .collect(Collectors.toList());

            this.peoples = peoples;
            this.peopleCount = peoples.size();
        }

        @Getter
        public static class MenuDTO {
            private Long idx;
            private String menuName;
            private int menuPrice;
            private List<OptionDTO> options;

            public MenuDTO(List<Option> options) {
                this.idx = options.get(0).getMenu().getIdx();
                this.menuName = options.get(0).getMenu().getMenuName();
                this.menuPrice = options.stream()
                        .mapToInt(option -> option.getPrice())
                        .sum();
                this.options = options.stream()
                        .map(OptionDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public static class OptionDTO {
                private Long idx;
                private String optionName;
                private int optionPrice;

                public OptionDTO(Option option) {
                    this.idx = option.getIdx();
                    this.optionName = option.getOptionName();
                    this.optionPrice = option.getPrice();
                }
            }
        }
    }

    @Getter
    public static class FindAllDTO {
        private List<OrderDTO> orders;

        public FindAllDTO(List<Order> orders, List<Item> items, List<Delivery> deliveries) {
            this.orders = orders.stream()
                    .map(order -> new OrderDTO(order, items, deliveries))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class OrderDTO {
            private Long idx;
            private String image;
            private String createAt;
            private String shopName;
            private String orderName;
            private List<ItemDTO> items;
            private String status;
            private int price;

            public OrderDTO(Order order, List<Item> items, List<Delivery> deliveries) {
                this.idx = order.getIdx();
                Item firstItem = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No matching items found for order"));
                this.image = firstItem.getShop().getBrand().getImage();
                this.createAt = firstItem.getCreateAt().toString();
                this.shopName = firstItem.getShop().getShopName();
                this.orderName = order.getOrderName();
                this.items = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
                this.status = getDeliveryStatus(order, deliveries);
                this.price = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .mapToInt(Item::getPrice)
                        .sum();
            }

            private String getDeliveryStatus(Order order, List<Delivery> deliveries) {
                Delivery findDelivery = deliveries.stream()
                        .filter(delivery -> delivery.getOrder().getIdx() == order.getIdx())
                        .findFirst()
                        .orElseThrow(() -> new Exception404("해당 배달을 찾을 수 없습니다."));

                if(findDelivery.getStatus() == 'Y') {
                    return "배달 완료";
                }

                return "배달 중";
            }

            /*public OrderDTO(Order order, List<Item> items) {
                this.idx = order.getIdx();
                this.image = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getShop().getBrand().getImage();
                this.createAt = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getCreateAt().toString();
                this.shopName = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getShop().getShopName();
                this.items = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
                this.price = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .stream()
                        .mapToInt(item -> item.getPrice())
                        .sum();
            }*/

            @Getter
            public static class ItemDTO {
                private Long idx;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.idx = item.getIdx();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }

    @Getter
    public static class FindDTO {
        private Long idx;
        private String shopName;
        private String createAt;
        private String address;
        private String status;
        private int totalPrice;
        private List<MenuDTO> menus;

        public FindDTO(Order order, List<Item> items, Delivery delivery) {
            this.idx = order.getIdx();
            this.shopName = items.get(0).getShop().getShopName();
            this.createAt = items.get(0).getCreateAt().toString();
            this.address = delivery.getAddress();
            this.status = getDeliveryStatus(delivery);
            this.totalPrice = items.stream()
                    .mapToInt(item -> item.getPrice())
                    .sum();

            Map<String, List<Item>> OptionsByMenu = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getMenu().getMenuName()));

            this.menus = OptionsByMenu.entrySet().stream()
                    .map(entry -> new MenuDTO(entry.getValue()))
                    .collect(Collectors.toList());

        }

        private String getDeliveryStatus(Delivery delivery) {
            if(delivery.getStatus() == 'Y') {
                return "배달 완료";
            }

            return "배달중";
        }

        @Getter
        public static class MenuDTO {
            private Long idx;
            private String menuName;
            private List<ItemDTO> items;

            public MenuDTO(List<Item> items) {
                this.idx = items.get(0).getOption().getMenu().getIdx();
                this.menuName = items.get(0).getOption().getMenu().getMenuName();
                this.items = items.stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public static class ItemDTO {
                private Long idx;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.idx = item.getIdx();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }

    @Getter
    public static class FindCancelDTO {
        private List<OrderDTO> orders;

        public FindCancelDTO(List<Order> orders, List<Item> items) {
            this.orders = orders.stream()
                    .map(order -> new OrderDTO(order, items))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class OrderDTO {
            private Long idx;
            private String image;
            private String createAt;
            private String shopName;
            private String orderName;
            private List<ItemDTO> items;
            private String status;
            private int price;

            public OrderDTO(Order order, List<Item> items) {
                this.idx = order.getIdx();
                Item firstItem = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No matching items found for order"));
                this.image = firstItem.getShop().getBrand().getImage();
                this.createAt = firstItem.getCreateAt().toString();
                this.shopName = firstItem.getShop().getShopName();
                this.orderName = order.getOrderName();
                this.items = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
                this.status = "취소됨";
                this.price = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .mapToInt(Item::getPrice)
                        .sum();
            }

            /*public OrderDTO(Order order, List<Item> items) {
                this.idx = order.getIdx();
                this.image = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getShop().getBrand().getImage();
                this.createAt = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getCreateAt().toString();
                this.shopName = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .get(0).getShop().getShopName();
                this.items = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
                this.price = items.stream()
                        .filter(item -> item.getOrder().getIdx() == order.getIdx())
                        .collect(Collectors.toList())
                        .stream()
                        .mapToInt(item -> item.getPrice())
                        .sum();
            }*/

            @Getter
            public static class ItemDTO {
                private Long idx;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.idx = item.getIdx();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }
}
