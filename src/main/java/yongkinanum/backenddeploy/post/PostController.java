package yongkinanum.backenddeploy.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody @Valid PostRequest.WriteDTO writeDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.writePost(writeDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        PostResponse.FindAllDTO findAllDTO = postService.findAllPost();

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        PostResponse.FindDTO findDTO = postService.findPost(id);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findSpecific(@RequestBody PostRequest.FindSpecificDTO findSpecificDTO) {
        PostResponse.FindSpecificDTO findDTO = postService.findSpecificPosts(findSpecificDTO);

        return ResponseEntity.ok().body(ApiUtils.success(findDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PostRequest.UpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(id, updateDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
