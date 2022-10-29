package news.data.collect.pro.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @GetMapping(value = "/main")
    public String home() {
        return "content/main";
    }

    @GetMapping(value = "/setting")
    public String setting() {
        return "content/setting";
    }
}
