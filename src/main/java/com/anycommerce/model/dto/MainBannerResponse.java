package com.anycommerce.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


import java.util.List;

@Data
@Builder
public class MainBannerResponse {
    private List<BannerResponse> banners;

}
