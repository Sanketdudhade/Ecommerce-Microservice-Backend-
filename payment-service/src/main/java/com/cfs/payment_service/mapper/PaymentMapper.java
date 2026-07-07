package com.cfs.payment_service.mapper;

import com.cfs.payment_service.dto.response.PaymentHistoryResponse;
import com.cfs.payment_service.dto.response.PaymentResponse;
import com.cfs.payment_service.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);

    PaymentHistoryResponse toHistoryResponse(Payment payment);

    List<PaymentHistoryResponse> toHistoryResponseList(List<Payment> payments);

}