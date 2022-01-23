package hello.servlet.web.frontcontoller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 { // 다형성을 위해 Interface 로 생성
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
