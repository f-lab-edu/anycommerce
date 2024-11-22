package com.anycommerce.model.dto;

import java.util.List;

public class SmsRequestDTO {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageDTO> messages;
}
