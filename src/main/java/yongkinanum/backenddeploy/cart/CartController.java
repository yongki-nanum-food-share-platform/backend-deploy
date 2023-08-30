package yongkinanum.backenddeploy.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody List<CartRequest.AddDTO> addDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCarts(addDTOs, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> find(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindDTO findDTO = cartService.findCarts(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> updateDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.updateCarts(updateDTOs, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/clear")
    public ResponseEntity delete(@AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.clearCarts(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
