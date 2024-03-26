package com.yu.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * SpringSecurity验证码问题
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
