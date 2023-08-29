package yongkinanum.backenddeploy.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody @Valid ShopRequest.RegistDTO registDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shopService.registShop(registDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
         ShopResponse.FindDTO findDTO = shopService.findShop(id);

         return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ShopRequest.UpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shopService.updateShopInfo(updateDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/unregist")
    public ResponseEntity<?> unregist(@RequestBody ShopRequest.UnregistDTO unregistDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shopService.unregistShop(unregistDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
