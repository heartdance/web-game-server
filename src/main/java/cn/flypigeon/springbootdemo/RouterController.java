package cn.flypigeon.springbootdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by htf on 2020/11/4.
 */
@Controller
public class RouterController {

    @RequestMapping(value = {"", "/", "/{x:[\\w\\-]+}", "/{x:^(?!api|ws$).*$}/**/{y:[\\w\\-]+}"})
    public String indexPage() {
        return "/index.html";
    }
}
