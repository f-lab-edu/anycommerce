package com.anycommerce.service;

import com.anycommerce.model.entity.SmsLog;
import com.anycommerce.repository.SmsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

    private final SmsRepository smsRepository;
    private DefaultMessageService messageService;

    @Value("${coolsms-api-key}")
    private String apiKey;

    @Value("${coolsms-api-secret}")
    private String apiSecret;

    @PostConstruct
    private void initializeMessageService() {
        // DefaultMessageService 초기화
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }


    /**
     * SMS 발송 및 로그 저장
     */
    public boolean sendSms(String from,String phoneNumber, String messageText) {
        Message message = new Message();
        message.setFrom(from); // 발신번호
        message.setTo(phoneNumber); // 수신번호
        message.setText(messageText); // 메시지 내용

        try {
            // 외부 API 호출
            messageService.sendOne(new SingleMessageSendingRequest(message));

            // 성공 로그 저장
            SmsLog smsLog = SmsLog.builder()
                    .to(phoneNumber)
                    .text(messageText)
                    .sentAt(LocalDateTime.now())
                    .status("SUCCESS")
                    .build();
            smsRepository.save(smsLog);

            log.info("SMS 발송 성공: {} -> {}", phoneNumber, messageText);
            return true;

        } catch (Exception e) {
            // 실패 로그 저장
            SmsLog smsLog = SmsLog.builder()
                    .from(phoneNumber)
                    .to(messageText)
                    .sentAt(LocalDateTime.now())
                    .status("FAILED")
                    .build();
            smsRepository.save(smsLog);

            log.error("SMS 발송 실패: {} -> {}", phoneNumber, e.getMessage());
            return false;
        }
    }

}
