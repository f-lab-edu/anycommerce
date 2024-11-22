package com.anycommerce.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDTO {
    String to;
    String Content;
}
