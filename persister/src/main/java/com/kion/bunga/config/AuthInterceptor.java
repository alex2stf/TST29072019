package com.kion.bunga.config;

import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

  final ApplicationProperties applicationProperties;

  public AuthInterceptor(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    boolean forward = true;
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;

      Authorized authorization = hm.getMethodAnnotation(Authorized.class);


      if (authorization != null) {
        forward = false;
        String auth = request.getHeader("Authorization");
        if (StringUtils.hasText(auth)) {
          try {
            auth = auth.split(" ")[1];
            byte[] bytes = Base64Utils.decodeFromString(auth);
            String decoded = new String(bytes, Charset.forName("UTF-8"));
            String parts[] = decoded.split(":");
            if (parts.length == 2 && parts[0].equals(applicationProperties.getUsername()) && parts[1].equals(applicationProperties.getPassword())) {
              forward = true;
            }
          }catch (Exception ex){
            forward = false;
          }
        }
      }
      if (!forward){
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing.");
      }
    }
    return forward;
  }
}
