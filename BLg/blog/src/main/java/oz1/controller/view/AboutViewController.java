package oz1.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: ykk
 * @date: 2023/4/22 18:31
 * @version: 1.0
 */
@Controller
@RequestMapping("/")
public class AboutViewController {

    @GetMapping("/aboutme")
    public String aboutme(){

        return "about";
    }
}
