package hello.servlet.web.frontcontoller.v5;

import hello.servlet.web.frontcontoller.ModelView;
import hello.servlet.web.frontcontoller.MyView;
import hello.servlet.web.frontcontoller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontoller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontoller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontoller.v4.ControllerV4;
import hello.servlet.web.frontcontoller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontoller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontoller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontoller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontoller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    // Object 사용 이유 : ControllerV3, V4 등 버전과 관계없이 사용하기 위함.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapter();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyHandlerAdapter myHandlerAdapter = getHandlerAdapter(handler);
        ModelView modelView = myHandlerAdapter.handle(request,response,handler);

        String viewName = modelView.getViewName();
        MyView myView = viewResolver(viewName);

        myView.render(modelView.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private void initHandlerMappingMap() {
        // V3 이용
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapter() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        // V4 추가
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalArgumentException("handler Adapter를 찾을 수 없습ㄴ디ㅏ. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
