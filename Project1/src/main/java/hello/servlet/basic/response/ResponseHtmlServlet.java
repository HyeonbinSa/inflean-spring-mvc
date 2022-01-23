package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: text/html;charset=urf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println("<html>");
        printWriter.println("<body>");
        printWriter.println(" <div>안녕?</div>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }
}
