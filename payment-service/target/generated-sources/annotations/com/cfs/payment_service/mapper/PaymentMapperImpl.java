package com.cfs.payment_service.mapper;

import com.cfs.payment_service.dto.response.PaymentHistoryResponse;
import com.cfs.payment_service.dto.response.PaymentResponse;
import com.cfs.payment_service.entity.Payment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-07T15:55:20+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder paymentResponse = PaymentResponse.builder();

        paymentResponse.id( payment.getId() );
        paymentResponse.orderId( payment.getOrderId() );
        paymentResponse.customerId( payment.getCustomerId() );
        paymentResponse.paymentReference( payment.getPaymentReference() );
        paymentResponse.razorpayOrderId( payment.getRazorpayOrderId() );
        paymentResponse.razorpayPaymentId( payment.getRazorpayPaymentId() );
        paymentResponse.amount( payment.getAmount() );
        if ( payment.getCurrency() != null ) {
            paymentResponse.currency( payment.getCurrency().name() );
        }
        paymentResponse.paymentMethod( payment.getPaymentMethod() );
        paymentResponse.paymentGateway( payment.getPaymentGateway() );
        paymentResponse.paymentStatus( payment.getPaymentStatus() );
        paymentResponse.failureReason( payment.getFailureReason() );
        paymentResponse.remarks( payment.getRemarks() );
        paymentResponse.paidAt( payment.getPaidAt() );
        paymentResponse.createdAt( payment.getCreatedAt() );
        paymentResponse.updatedAt( payment.getUpdatedAt() );

        return paymentResponse.build();
    }

    @Override
    public PaymentHistoryResponse toHistoryResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentHistoryResponse.PaymentHistoryResponseBuilder paymentHistoryResponse = PaymentHistoryResponse.builder();

        paymentHistoryResponse.orderId( payment.getOrderId() );
        paymentHistoryResponse.paymentReference( payment.getPaymentReference() );
        paymentHistoryResponse.amount( payment.getAmount() );
        if ( payment.getCurrency() != null ) {
            paymentHistoryResponse.currency( payment.getCurrency().name() );
        }
        paymentHistoryResponse.paymentMethod( payment.getPaymentMethod() );
        paymentHistoryResponse.paymentStatus( payment.getPaymentStatus() );
        paymentHistoryResponse.paidAt( payment.getPaidAt() );
        paymentHistoryResponse.createdAt( payment.getCreatedAt() );

        return paymentHistoryResponse.build();
    }

    @Override
    public List<PaymentHistoryResponse> toHistoryResponseList(List<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentHistoryResponse> list = new ArrayList<PaymentHistoryResponse>( payments.size() );
        for ( Payment payment : payments ) {
            list.add( toHistoryResponse( payment ) );
        }

        return list;
    }
}
