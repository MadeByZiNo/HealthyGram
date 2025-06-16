package madebyzino.HealthyGram.domain.feed;

import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.infra.security.user.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/feeds")
@RequiredArgsConstructor
@RestController
public class FeedApiController {

    private final FeedService feedService;
    @GetMapping
    public ResponseEntity<FeedResponse> getFeedById(@RequestParam Long id) {
        FeedResponse feed = feedService.getFeed(id);
        return ResponseEntity.ok(feed);
    }

    @PostMapping
    public ResponseEntity<Feed> createFeed (@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestPart("request") FeedCreateRequest request,
                                            @RequestPart("images") List<MultipartFile> images) {
        Feed feed = feedService.createFeed(Long.valueOf(userDetails.getId()), request, images);
        return ResponseEntity.ok(feed);
    }
}
