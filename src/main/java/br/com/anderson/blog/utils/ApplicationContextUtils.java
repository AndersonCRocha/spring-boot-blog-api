package br.com.anderson.blog.utils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ApplicationContextUtils {

  private ApplicationContextUtils() { }

  public static <T> T getBean(Class<T> beanClass) {
    ServletContext servletContext = getCurrentHttpRequest().getServletContext();
    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    return Objects.requireNonNull(webApplicationContext).getBean(beanClass);
  }

  public static HttpServletRequest getCurrentHttpRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes instanceof ServletRequestAttributes) {
      return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    throw new RuntimeException("Unable to get current request");
  }

}
