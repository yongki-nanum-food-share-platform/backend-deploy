package yongkinanum.backenddeploy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.SaveDTO saveDTO = orderService.saveOrder(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(saveDTO));
    }

    @PostMapping("/info")
    public ResponseEntity<?> orderInfo(@RequestBody OrderRequest.InfoDTO infoDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.InfoDTO orderInfoDTO = orderService.findOrderInfo(infoDTO);

        return ResponseEntity.ok().body(ApiUtils.success(orderInfoDTO));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindAllDTO findAllDTO = orderService.findAllOrders(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindDTO findDTO = orderService.findOrder(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> findCancel(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindCancelDTO findCancelDTO = orderService.findCancelOrder(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findCancelDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.cancelOrder(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
