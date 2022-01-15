## Inflean - Spring MVC  학습

---

### 공부 순서

1. Servlet을 이용한 Web 
2. JSP의 사용과 MVC 패턴 적용
3. Front Controller 적용

---

### Front Controller

- Front Controller Pattern : 하나의 서블릿으로 Client의 요청을 받아 각 요청에  해당하는 Controller 호출
  - 장점
    - 중복된 코드를 제거
    - Front Controller 이외에 Controller에서 Servlet을 사용하지 않아도 됨.

---

### Ver 1. Front Controller의 도입

1. Controller 기능을 수행하는 클래스들을 Interface를 생성하여 다형성을 이용하도록 수정한다.
2. 각 요청 URL별로 수행하는 Controller Class 정보를 매핑하여 요청이 들어올 때 수행 Controller에게 정보를 전달.
   - `urlpatterns="/front-controller/v1/*"`을 통해 하위 경로로 들어오는 요청이 거치도록 설정
   - Controller Mapping 정보 
     - Key : 요청 URL (`/fornt-controller/v1/members/save`)
     - Value : 호출되는 Controller (`MemberSaveControllerV1`)
3. 각 Controller의 기능을 수행 -> JSP 실행

---

### Ver 2. Front Controller - View 분리

1.  현재까지 Code를 보면 Controller  ->  View 를 호출하는 부분이 중복이 된다.

   ```java
   // Contoller 내부 View Rendering 기능
   ...
   String viewPath = "/WEB-INF/views/save-result.jsp";
   RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
   requestDispatcher.forward(request, response);
   ..
   ```

2. 1번에서 사용하는 부분을 하나의 Class(Ex. MyView)로 생성하여 중복을 제거한다.

   ```java
   public class MyView {
       private String viewPath;
   
       public MyView(String viewPath) {
           this.viewPath = viewPath;
       }
   
       public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
           requestDispatcher.forward(request, response);
       }
   }
   ```

3. 요청받은 각각의 Contoller에서 Front Controller에 해당하는 View Class를 생성하여 반환

   ```java
   // 각각의 Contoller 수행 기능 
   ...
   public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		...
       return new MyView("/WEB-INF/views/save-result.jsp");
   }
   ```

4. Front Controller에서 전달받은 View 기능  수행

---

### Ver 3. Front Controller - Model 추가 

1. 보여질 View File 경로를 지정해주는 코드에 반복적인 부분이 존재함. (View 이름 중복)

   ```java
   ... // (Ver 2. 코드 중)
   // 각 Controller에서 MyView를 리턴할 때 "/WEB-INF/views/*/.jsp"가 중복적으로 사용된다.
   return new MyView("/WEB-INF/views/members.jsp");
   ```

2. 각각의 기능을 수행하는 Controller에서 불필요한 HttpServletRequest/Response 등이 중복된다. (Servlet 종속성)

   ```java
   public class MemberFormControllerV2 implements ControllerV2 {
     @Override
     public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     ...// 각 Contoller 별로 HttpServletRequest request, HttpServletResponse response 코드 포함
     }
   }
   ```

3. 각 기능 Controller에서는 View의 논리적 이름만 반환한도록 구현하고 실제 처리는 FrontController에서 처리하도록 구현(Servlet 종속석 제거를 위함.)

   ```java
   // Contoller 중 회원 목록 반환 Controller
   public class MemberListControllerV3 implements ControllerV3 {
       private MemberRepository memberRepository = MemberRepository.getInstance();
   		// Controller에서는 View의 논리적인 이름만 반환하도록 함
       @Override
       public ModelView process(Map<String, String> paramMap) {
           List<Member> members = memberRepository.findAll();
           ModelView modelView = new ModelView("members");
           modelView.getModel().put("members", members);
   	      return modelView;
      	}
   }
   ```

4. 기능 Controller를 통해 전달받은 View의 논리적 이름으로 View의 **물리적 위치와 이름** 처리 (View 이름 중복 제거를 위함.)

   ```java
   ... // Front Controller Code 중 View 물리적 이름과 위치 반환.
   private MyView viewResolver(String viewName) {
       return new MyView("/WEB-INF/views/" + viewName + ".jsp");
   }
   ```

5. Front Controller에서 Request(요청)으로 전달받은 값들을 Model로 만들어 View로 전달하도록 구현.

---

### Ver 4. Front Controller -  Controller 코드 간소화

1. 각 Controller의 Parameter로 Model을 넣어 코드를 간소화한다.

   ```java
   public interface ControllerV4 {
       String process(Map<String, String> paramMap, Map<String, Object> model);
   }
   ```

2. Parameter에 model을 넣음으로써 Controller에서 ModelView 관련 코드를 제거하고 단순 View Name만 반환하도록 설정

   ```java
   // MemberListControllerV4 코드 중 
   public String process(Map<String, String> paramMap, Map<String, Object> model) {
       List<Member> members = memberRepository.findAll();
       model.put("members", members);
   	  // ModelView modelView = new ModelView("members"); //제거
   		// modelView.getModel().put("members", members); //제거
       return "members";
   }
   ```

3. 즉, ControllerV3와 다르게 Model을 ControllerV4의 인자로 사용하여 ModelView에 대한 코드를 줄이고 각 Controller의 코드를 간소화.

---

### Spring MVC

#### DispatcherServlet

- DispatcherServlet은 Spring MVC에서 Front Controller 역할을 수행한다.
- HttpServlet을 상속받아 사용하며 Servlet으로 동작한다.
  - `DispatcherServlet` -> FrameworkServlet -> HttpServletBean -> `HttpServlet`
- Spring Boot는 DispatcherServlet을 서블릿으로 자동으로 등록하고 모든 경로에 대해 맵핑한다.
- `doDispatch()`의 역할이 가장 중요하다.

#### HandlerMapping, HandlerAdapter

- HandlerMapping 에서 Controller를 찾는다. 
  - Spring Bean Name으로 Handler 탐색 > BeanNameUrlHandlerMapping
  - `Bean Name으로 탐색하여 Handler(OldController)  반환`
- HandlerMapping에서 찾은 Handler를 실행할 HandlerAdapter가 필요
  - Controller Interface(@Controller가 아님) 처리 > SimpleControllerHandlerAdapter
  - `SimpleControllerHandlerAdapter가 Handler(OldController)를 내부에서 실행 후 결과 반환.`

#### View Resolver

- Spring Boot에서 `InternalResourceViewResolver`라는 View Resolver를 자동으로 등록
- resouces>apllication.properties 파일  안에 있는 설정 정보를 통해  등록
  - `spring.mvc.view.prefix`
  - `spring.mvc.view.suffix`
