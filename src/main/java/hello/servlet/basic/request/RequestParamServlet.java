package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 전체 파라미터를 조회할 경우
        System.out.println("[전체 파라미터 조회] - start");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String result = request.getParameter(parameterName);
            System.out.println(parameterName + " = " + result);
        }
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        // 하나의 파라미터를 조회하고 싶은 경우
        System.out.println("[단일 파라미터 조회] - start");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age" + " = " + age);
        System.out.println("[단일 파라미터 조회] - end");
        System.out.println();

        // 동일한 파라미터 네임을 가진 값이 여러개일 경우
        System.out.println("[복수 파라미터 조회] - start");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
        System.out.println("[복수 파라미터 조회] - end");
        System.out.println();
    }
}