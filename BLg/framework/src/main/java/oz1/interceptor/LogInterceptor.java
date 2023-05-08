package oz1.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: ykk
 * @date: 2023/4/3 18:44
 * @version: 1.0
 */
public class LogInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response,
      Object handler) throws Exception {

    if (request.getSession().getAttribute("user") == null) {
      response.sendRedirect("/admin");
      return false;
    }
    return true;
  }
}
