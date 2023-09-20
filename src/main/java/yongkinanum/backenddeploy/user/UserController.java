package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.security.JwtProvider;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody @Valid UserRequest.RegistDTO registDTO, Errors errors) {
        userService.regist(registDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/address/add")
    public ResponseEntity<?> addAddress(@RequestBody UserRequest.AddAddressDTO addAddressDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.addUserAddress(addAddressDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/address")
    public ResponseEntity<?> findAddress(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse.FindAddressDTO findAddressDTO = userService.findUserAddresses(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findAddressDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
        String jwt = userService.login(loginDTO);

        return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> find(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse.FindDTO findDTO = userService.findUserInfo(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSpecificUser(@PathVariable Long id) {
        UserResponse.FindDTO findDTO = userService.findSpecificUserInfo(id);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @GetMapping("/{id}/shop")
    public ResponseEntity<?> findShop(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse.FindShopDTO findShopDTO = userService.findShopInfo(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findShopDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid UserRequest.UpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserInfo(updateDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/unregist")
    public ResponseEntity<?> unregist(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.unregistUser(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
