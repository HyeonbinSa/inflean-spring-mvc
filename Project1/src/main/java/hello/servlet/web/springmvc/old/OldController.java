package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller")
public class OldController implements Controller { //.web.servlet.mvc.Controll => 과거버전의 Controller
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}
/* Controller를  호출하기 위해 2가지 필요
1. 핸들러 매핑
    1) Annotation 기반의 Controller인 @RequestMapping에 따라 호출 >  RequestMappingHandlerMapping
    2) Spring Bean Name으로 Handler 탐색 > BeanNameUrlHandlerMapping
2. 핸들러 어댑터
    1) Annotation 기반의 Controller인 @RequestMapping에 따라 호출 >  RequestMappingHandlerAdapter
    2) HttpRequestHandler 처리 > HttpRequestHandlerAdapter
    3) Controller Interface(@Controller가 아님) 처리 > SimpleControllerHandlerAdapter
 */
