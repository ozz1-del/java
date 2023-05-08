/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:44:51 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-04 20:52:29
 * @Version: 2.0
 */
package oz1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import oz1.domain.entity.User;
import oz1.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping
  public String LoginPage() {
    return "admin/login";
  }

  @GetMapping("/")
  public String login() {
    return "admin/login";
  }

  @GetMapping("/index")
  public String index() {
    return "admin/index";
  }

  @PostMapping("/login")
  public String login(@RequestParam String username,
      @RequestParam String password,
      HttpSession session,
      RedirectAttributes attributes) {
    User user = userService.login(username, password);
    if (Objects.nonNull(user)) {
      user.setPassword(null);
      session.setAttribute("user", user);
      return "redirect:/admin/index";
    } else {
      attributes.addFlashAttribute("message", "用户名或密码错误");
      return "redirect:/admin";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/admin/index";
  }
}
