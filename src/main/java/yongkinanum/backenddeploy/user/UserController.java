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
