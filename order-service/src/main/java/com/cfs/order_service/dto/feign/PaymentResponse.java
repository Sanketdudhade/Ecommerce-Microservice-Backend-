package com.cfs.order_service.dto.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private String paymentId;

    private String orderNumber;

    private String paymentStatus;

    private String paymentUrl;

}