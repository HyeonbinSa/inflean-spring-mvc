package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("V1 : username={}, age={}", username, age);

        response.getWriter().write("Request Param V1 OK");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("V2 : username={}, age={}", memberName, memberAge);
        return "Request Param V2 OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("V3 : username={}, age={}", username, age);
        return "Request Param V3 OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("V4 : username={}, age={}", username, age);
        return "Request Param V4 OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // required = true 일 때 해당 값이 비어있다면 에러 발생
            @RequestParam(required = false) int age) { // required : 필수 파라미터 여부 설정 (Default는 true)

        log.info("required : username={}, age={}", username, age);
        return "Request Param Required OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest1") String username, // required = true 일 때 해당 값이 비어있다면 에러 발생
            @RequestParam(required = false, defaultValue = "19") int age) { // required : 필수 파라미터 여부 설정 (Default 는 true)

        log.info("default : username={}, age={}", username, age);
        return "Request Param Default OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("map : username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "Request Param Map OK";
    }
}
