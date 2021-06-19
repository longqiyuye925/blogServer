package com.example.blogserver.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/16
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    Log log = LogFactory.getLog(MyAccessDecisionManager.class);
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("MyAccessDecisionManager:decide come in");
        //如果请求路径没有匹配到权限表的数据，则直接放行
        if (null == collection || 0 == collection.size()) {
            log.info("MyAccessDecisionManager:decide collection isnull");
            return;
        }
        ConfigAttribute configAttribute;

        //把请求路径匹配到的权限和用户匹配到的权限进行比对，有一种权限就可以放行
        for (Iterator<ConfigAttribute> iterator = collection.iterator(); iterator.hasNext(); ) {
            configAttribute = iterator.next();
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (attribute.trim().equals(grantedAuthority.getAuthority())) {
                    log.info("MyAccessDecisionManager:decide 有匹配的权限");
                    return;
                }
            }
        }
        //如果前面都没有拦住，最后直接报错提示，无权限。这里抛异常后，后重新发起错误处理请求，打印下MyAbstractSecurityInterceptor里的日志，会发现进入两次
        //打印下httpRequest.getRequestURL()会发现，它的路径是/error
        log.info("MyAccessDecisionManager:decide no right");
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
