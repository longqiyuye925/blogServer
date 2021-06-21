package com.example.blogserver.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/19
 */
@Component
public class MyApplicationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final Log LOG = LogFactory.getLog(MyApplicationListener.class);
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        Object userName = authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal();
        Object credentials = authenticationFailureBadCredentialsEvent.getAuthentication().getCredentials();
        LOG.info("Failed login using USERNAME [" + userName + "]");
        LOG.info("Failed login using PASSWORD [" + credentials + "]");
    }
}
