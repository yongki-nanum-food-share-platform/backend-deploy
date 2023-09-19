
package yongkinanum.backenddeploy.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/enter")
    public ResponseEntity<?> enter(@RequestBody ChatRequest.EnterDTO enterDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatService.enterChat(enterDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ChatResponse.FindAllDTO findAllDTO = chatService.findAllChats(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        ChatResponse.FindDTO findDTO = chatService.findChat(id);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        chatService.deleteChat(id);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
