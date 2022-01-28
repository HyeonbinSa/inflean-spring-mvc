package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView modelAndView = new ModelAndView("response/hello")
                .addObject("data", "Hello!");
        return modelAndView;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data","Hello!(I'm V2~)");
        return "response/hello";
    }

    @RequestMapping("/response/hello") //Controller의 경로와 View의 논리적 이름이 같다면 Spring에서 자동으로 처리. 권장하지 않는 방법(불명확함)
    public void responseViewV3(Model model){
        model.addAttribute("data","Hello!(I'm V3~)");
    }

}
