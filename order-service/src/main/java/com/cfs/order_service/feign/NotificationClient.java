package com.cfs.order_service.feign;

import com.cfs.order_service.dto.feign.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationClient {

    @PostMapping("/api/notifications/email")
    void sendOrderConfirmation(@RequestBody NotificationRequest request);

}