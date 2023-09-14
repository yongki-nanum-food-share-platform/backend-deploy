package yongkinanum.backenddeploy.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yongkinanum.backenddeploy.core.security.CustomUserDetails;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody ReviewRequest.WriteDTO writeDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.writeReview(writeDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ReviewResponse.FindAllDTO findAllDTO = reviewService.findAllReviews(userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(findAllDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ReviewRequest.UpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.updateReview(id, updateDTO, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.deleteReview(id, userDetails.getUser());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
