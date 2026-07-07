package com.cfs.payment_service.constant;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String BASE_URL = "/api/payments";

    public static final String CREATE_PAYMENT = "/create";

    public static final String VERIFY_PAYMENT = "/verify";

    public static final String REFUND_PAYMENT = "/refund";

    public static final String CANCEL_PAYMENT = "/cancel/{paymentId}";

    public static final String GET_PAYMENT = "/{paymentId}";

    public static final String GET_PAYMENT_BY_ORDER = "/order/{orderId}";

    public static final String PAYMENT_HISTORY = "/history";

}