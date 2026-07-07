package com.cfs.order_service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class OrderNumberGenerator {

    public String generateOrderNumber() {

        String date = LocalDate.now().toString().replace("-", "");

        String random = UUID.randomUUID()
                .toString()
                .substring(0,8)
                .toUpperCase();

        return "ORD-" + date + "-" + random;
    }

}