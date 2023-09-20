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

    @PostMapping
    public ResponseEntity<?> findAll(@RequestBody ShopRequest.FindAllDTO findAllDTO) {
        ShopResponse.FindAllDTO findResponseDTO = shopService.findAllShops(findAllDTO);

        return ResponseEntity.ok().body(ApiUtils.success(findResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
         ShopResponse.FindDTO findDTO = shopService.findShop(id);

         return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @PostMapping("/brand")
    public ResponseEntity<?> findBrand(@RequestBody ShopRequest.FindBrandDTO findBrandDTO) {
        ShopResponse.FindBrandDTO findDTO = shopService.findBrand(findBrandDTO);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShopRequest.UpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shopService.updateShopInfo(id, updateDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/{id}/unregist")
    public ResponseEntity<?> unregist(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        shopService.unregistShop(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
