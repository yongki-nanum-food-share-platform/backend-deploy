package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
        userService.login(loginDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/")
    public ResponseEntity<?> find() {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update() {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/unregist")
    public ResponseEntity<?> unregist() {
        return ResponseEntity.ok("ok");
    }
}
