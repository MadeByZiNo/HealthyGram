package madebyzino.HealthyGram.domain.feed;

import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.common.exception.CustomException;
import madebyzino.HealthyGram.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedJpaRepository feedJpaRepository;

    public FeedResponse getFeed(Long id){

        /* feed가 private면 권한 확인 및 예외 추가
        if() {
            throw new CustomException(ErrorCode.FEED_ACCESS_DENIED);
        }
        */

        Feed feed = feedJpaRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_FEED));

        // Response로 가공
        //

        FeedResponse feedResponse = null;
        return feedResponse;
    }

    public Feed createFeed(Long userId, FeedCreateRequest request, List<MultipartFile> images){

        // 피드 및 이미지 저장
        // Feed feed = Feed.from(request);
        // feedJpaRepository.save(feed);

        // 이미지 저장
        /*
        if (images != null){
            for (MultipartFile file : images) {
                imageUploadService.uploadAndSaveProductImage(file, ImageFrom.PRODUCT.getDir(), product);
            }
        }*/

        return feed;
    }



}
