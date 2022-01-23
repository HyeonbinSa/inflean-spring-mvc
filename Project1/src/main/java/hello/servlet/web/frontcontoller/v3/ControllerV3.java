package hello.servlet.web.frontcontoller.v3;

import hello.servlet.web.frontcontoller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    // Servlet의 기술이 들어가있지 않음.
    ModelView process(Map<String, String> paramMap);
}
