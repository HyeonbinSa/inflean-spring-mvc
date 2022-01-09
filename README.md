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

### Ver 1. Front Controller의 도입

1. Controller 기능을 수행하는 클래스들을 Interface를 생성하여 다형성을 이용하도록 수정한다.
2. 각 요청 URL별로 수행하는 Controller Class 정보를 매핑하여 요청이 들어올 때 수행 Controller에게 정보를 전달.
   - `urlpatterns="/front-controller/v1/*"`을 통해 하위 경로로 들어오는 요청이 거치도록 설정
   - Controller Mapping 정보 
     - Key : 요청 URL (`/fornt-controller/v1/members/save`)
     - Value : 호출되는 Controller (`MemberSaveControllerV1`)
3. 각 Controller의 기능을 수행 -> JSP 실행

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
