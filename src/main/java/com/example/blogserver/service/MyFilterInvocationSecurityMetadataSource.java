package com.example.blogserver.service;

import com.alibaba.fastjson.JSON;
import com.example.blogserver.dao.PermisionDao;
import com.example.blogserver.entity.Permision;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.PathMatcher;
import java.util.*;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/16
 */
@Service
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    Log log = LogFactory.getLog(MyFilterInvocationSecurityMetadataSource.class);

    @Autowired
    PermisionDao permisionDao;
    private HashMap<String, Collection<ConfigAttribute>> map = null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        log.info("MyFilterInvocationSecurityMetadataSource:getAttributes come in");
        //1获取所有的路径对应的权限
        loadAllUrlResouceAuth();
        List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
        //2 从过滤器中取出请求request
        HttpServletRequest httpRequest = ((FilterInvocation) o).getHttpRequest();
        //3 匹配
        AntPathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = map.keySet().iterator();
        Collection<ConfigAttribute> collection = null;
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            String path = httpRequest.getRequestURI().toString();
            if (pathMatcher.match(pattern, path)) {
                log.info("MyFilterInvocationSecurityMetadataSource:getAttributes 有匹配的路径和权限");
                collection = map.get(pattern);
            }
        }
        //collection为null的时候，不会进入Manager
        log.info("MyFilterInvocationSecurityMetadataSource:getAttributes  -collection:" + JSON.toJSONString(collection));
        return collection;
    }

    private void loadAllUrlResouceAuth() {
        map = new HashMap<>();
        //从数据库查出所有的权限数据集合
        List<Permision> permisionList = permisionDao.findAll();
        ConfigAttribute cfg;
        Collection<ConfigAttribute> array;
        //这里把url作为k，权限名作为v
        for (Permision permision : permisionList) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permision.getName());
            array.add(cfg);
            map.put(permision.getUrl(), array);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
