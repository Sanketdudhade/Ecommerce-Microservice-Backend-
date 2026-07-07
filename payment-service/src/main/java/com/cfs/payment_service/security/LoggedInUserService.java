package com.cfs.payment_service.security;

public interface LoggedInUserService {

    Long getCurrentUserId();

    String getCurrentUserEmail();

}