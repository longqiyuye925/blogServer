package com.example.blogserver.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/25
 */
@RestController
public class UserController {
    @RequestMapping("/index")
    public Object index(@AuthenticationPrincipal AuthenticationPrincipal authenticationPrincipal, HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        return Jwts.parser().setSigningKey("wsc".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }
}
