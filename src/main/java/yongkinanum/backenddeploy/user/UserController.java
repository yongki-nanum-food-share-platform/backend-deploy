package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody UserRequest.RegistDTO registDTO) {
        userService.regist(registDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        System.out.println("true = " + true);
        return ResponseEntity.ok("ok");
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
