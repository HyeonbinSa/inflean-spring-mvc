package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller 사용 시 viewName을 반환하지만 @RestController 사용 시 해당 문자열 그대로 반환
// HTTP Message Body 에 바로 입력
@RestController
@Slf4j // lombok이 Logger 자동 주입
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass()); // 현재 Class 지정

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "String";

        System.out.println("name = " + name);
        //name = String
        log.info(" info log={}", name);
        //2022-01-23 20:25:50.041  INFO 7391 --- [nio-8080-exec-2] hello.springmvc.basic.LogTestController  :  info log=String

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);
        return "ok";
    }
}
