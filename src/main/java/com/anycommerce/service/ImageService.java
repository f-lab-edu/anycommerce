package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.ImageResponse;
import com.anycommerce.model.entity.Image;
import com.anycommerce.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int JUMBO_DEFAULT_WIDTH = 1200;
    private static final int JUMBO_DEFAULT_HEIGHT = 600;
    private static final String DEFAULT_FORMAT = "png";
    private ImageRepository imageRepository;

    public ImageResponse getLogoImage() {
        // 로고 이미지 정보 생성
        return ImageResponse.builder()
                .imageUrl("/images/logo.png")
                .width(100)
                .height(50)
                .description("로고 이미지")
                .format("png")
                .build();
    }

    public List<ImageResponse> getJumbotronImages() {
        // 점보트론 이미지 경로
        try {
        List<String> imageUrls = List.of(
                "/images/jumbotron1.png",
                "/images/jumbotron2.png",
                "/images/jumbotron3.png",
                "/images/jumbotron4.png",
                "/images/jumbotron5.png",
                "/images/jumbotron6.png"
            );

            // 각 URL에 대해 ImageResponse 생성
            return imageUrls.stream()
                .map(url -> {
                    // 유효하지 않은 이미지 URL 확인
                    if (!isValidImage(url)) {
                        throw new CustomBusinessException(ErrorCode.IMAGE_LOAD_FAILED);
                    }
                    return ImageResponse.builder()
                            .imageUrl(url)
                            .width(JUMBO_DEFAULT_WIDTH)
                            .height(JUMBO_DEFAULT_HEIGHT)
                            .description("점보트론 이미지: " + url)
                            .format(DEFAULT_FORMAT)
                            .build();
                })
                .toList();

        } catch (Exception e) {
            throw new CustomBusinessException(ErrorCode.IMAGE_LOAD_FAILED, e, null, null);
        }

    }

    private boolean isValidImage(String imageUrl) {
        // 이미지 유효성 검사 로직 (파일 존재 여부 확인)
        return imageUrl != null; // 예시 조건
    }

    // 이미지 아이디로 이미지 가져오기
    public ImageResponse getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        return ImageResponse.builder()
                .imageUrl(image.getImageUrl())
                .width(image.getWidth())
                .height(image.getHeight())
                .description(image.getDescription())
                .format(image.getFormat())
                .build();
    }

    // 상품 아이디로 이미지 가져오기
    public List<ImageResponse> getImagesByProductId(Long productId) {
        List<Image> images = imageRepository.findByProductId(productId);

        return images.stream()
                .map(image -> ImageResponse.builder()
                        .imageUrl(image.getImageUrl())
                        .width(image.getWidth())
                        .height(image.getHeight())
                        .description(image.getDescription())
                        .format(image.getFormat())
                        .build())
                .toList();
    }

}
