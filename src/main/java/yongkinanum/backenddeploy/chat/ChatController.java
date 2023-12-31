
package yongkinanum.backenddeploy.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.security.JwtProvider;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{id}")
    @SendTo("/chats/{id}")
    public ResponseEntity<?> receiveAndSend(@DestinationVariable Long id, @RequestBody ChatRequest.ReceiveDTO receiveDTO) {
        ChatResponse.SendDTO sendDTO = chatService.receiveAndSendChat(id, receiveDTO);

        return ResponseEntity.ok().body(ApiUtils.success(sendDTO));
    }


    @PostMapping("/chats/enter")
    public ResponseEntity<?> enter(@RequestBody ChatRequest.EnterDTO enterDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatService.enterChat(enterDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/chats")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ChatResponse.FindAllDTO findAllDTO = chatService.findAllChats(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        ChatResponse.FindDTO findDTO = chatService.findChat(id);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @DeleteMapping("/chats/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        chatService.deleteChat(id);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
