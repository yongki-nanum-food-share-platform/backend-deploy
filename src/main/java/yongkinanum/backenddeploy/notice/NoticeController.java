package yongkinanum.backenddeploy.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    public final NoticeService noticeService;

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody NoticeRequest.WriteDTO writeDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        noticeService.writeNotice(writeDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        NoticeResponse.FindAllDTO findAllDTO = noticeService.findAllNotices();

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }
}
