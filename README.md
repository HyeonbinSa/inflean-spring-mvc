# Inflean - Spring MVC  ํ์ต


## ๐ ๊ณต๋ถ ์์

### Project 1 - Servlet, JSP, MVC ํจํด์ ์ด์ฉํ Spring MVC ๊ตฌ์กฐ ์ดํด
1. Servlet์ ์ด์ฉํ Web 
2. JSP์ ์ฌ์ฉ๊ณผ MVC ํจํด ์ ์ฉ
3. Front Controller ์ ์ฉ
### Project 2 - Spring MVC๋ฅผ ํ์ฉํ Web Project

---
## ๐ Project 1

### Front Controller

- Front Controller Pattern : ํ๋์ ์๋ธ๋ฆฟ์ผ๋ก Client์ ์์ฒญ์ ๋ฐ์ ๊ฐ ์์ฒญ์  ํด๋นํ๋ Controller ํธ์ถ
  - ์ฅ์ 
    - ์ค๋ณต๋ ์ฝ๋๋ฅผ ์ ๊ฑฐ
    - Front Controller ์ด์ธ์ Controller์์ Servlet์ ์ฌ์ฉํ์ง ์์๋ ๋จ.


### Ver 1. Front Controller์ ๋์

1. Controller ๊ธฐ๋ฅ์ ์ํํ๋ ํด๋์ค๋ค์ Interface๋ฅผ ์์ฑํ์ฌ ๋คํ์ฑ์ ์ด์ฉํ๋๋ก ์์ ํ๋ค.
2. ๊ฐ ์์ฒญ URL๋ณ๋ก ์ํํ๋ Controller Class ์ ๋ณด๋ฅผ ๋งคํํ์ฌ ์์ฒญ์ด ๋ค์ด์ฌ ๋ ์ํ Controller์๊ฒ ์ ๋ณด๋ฅผ ์ ๋ฌ.
   - `urlpatterns="/front-controller/v1/*"`์ ํตํด ํ์ ๊ฒฝ๋ก๋ก ๋ค์ด์ค๋ ์์ฒญ์ด ๊ฑฐ์น๋๋ก ์ค์ 
   - Controller Mapping ์ ๋ณด 
     - Key : ์์ฒญ URL (`/fornt-controller/v1/members/save`)
     - Value : ํธ์ถ๋๋ Controller (`MemberSaveControllerV1`)
3. ๊ฐ Controller์ ๊ธฐ๋ฅ์ ์ํ -> JSP ์คํ


### Ver 2. Front Controller - View ๋ถ๋ฆฌ

1.  ํ์ฌ๊น์ง Code๋ฅผ ๋ณด๋ฉด Controller  ->  View ๋ฅผ ํธ์ถํ๋ ๋ถ๋ถ์ด ์ค๋ณต์ด ๋๋ค.

   ```java
   // Contoller ๋ด๋ถ View Rendering ๊ธฐ๋ฅ
   ...
   String viewPath = "/WEB-INF/views/save-result.jsp";
   RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
   requestDispatcher.forward(request, response);
   ..
   ```

2. 1๋ฒ์์ ์ฌ์ฉํ๋ ๋ถ๋ถ์ ํ๋์ Class(Ex. MyView)๋ก ์์ฑํ์ฌ ์ค๋ณต์ ์ ๊ฑฐํ๋ค.

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

3. ์์ฒญ๋ฐ์ ๊ฐ๊ฐ์ Contoller์์ Front Controller์ ํด๋นํ๋ View Class๋ฅผ ์์ฑํ์ฌ ๋ฐํ

   ```java
   // ๊ฐ๊ฐ์ Contoller ์ํ ๊ธฐ๋ฅ 
   ...
   public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		...
       return new MyView("/WEB-INF/views/save-result.jsp");
   }
   ```

4. Front Controller์์ ์ ๋ฌ๋ฐ์ View ๊ธฐ๋ฅ  ์ํ


### Ver 3. Front Controller - Model ์ถ๊ฐ 

1. ๋ณด์ฌ์ง View File ๊ฒฝ๋ก๋ฅผ ์ง์ ํด์ฃผ๋ ์ฝ๋์ ๋ฐ๋ณต์ ์ธ ๋ถ๋ถ์ด ์กด์ฌํจ. (View ์ด๋ฆ ์ค๋ณต)

   ```java
   ... // (Ver 2. ์ฝ๋ ์ค)
   // ๊ฐ Controller์์ MyView๋ฅผ ๋ฆฌํดํ  ๋ "/WEB-INF/views/*/.jsp"๊ฐ ์ค๋ณต์ ์ผ๋ก ์ฌ์ฉ๋๋ค.
   return new MyView("/WEB-INF/views/members.jsp");
   ```

2. ๊ฐ๊ฐ์ ๊ธฐ๋ฅ์ ์ํํ๋ Controller์์ ๋ถํ์ํ HttpServletRequest/Response ๋ฑ์ด ์ค๋ณต๋๋ค. (Servlet ์ข์์ฑ)

   ```java
   public class MemberFormControllerV2 implements ControllerV2 {
     @Override
     public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     ...// ๊ฐ Contoller ๋ณ๋ก HttpServletRequest request, HttpServletResponse response ์ฝ๋ ํฌํจ
     }
   }
   ```

3. ๊ฐ ๊ธฐ๋ฅ Controller์์๋ View์ ๋ผ๋ฆฌ์  ์ด๋ฆ๋ง ๋ฐํํ๋๋ก ๊ตฌํํ๊ณ  ์ค์  ์ฒ๋ฆฌ๋ FrontController์์ ์ฒ๋ฆฌํ๋๋ก ๊ตฌํ(Servlet ์ข์์ ์ ๊ฑฐ๋ฅผ ์ํจ.)

   ```java
   // Contoller ์ค ํ์ ๋ชฉ๋ก ๋ฐํ Controller
   public class MemberListControllerV3 implements ControllerV3 {
       private MemberRepository memberRepository = MemberRepository.getInstance();
   		// Controller์์๋ View์ ๋ผ๋ฆฌ์ ์ธ ์ด๋ฆ๋ง ๋ฐํํ๋๋ก ํจ
       @Override
       public ModelView process(Map<String, String> paramMap) {
           List<Member> members = memberRepository.findAll();
           ModelView modelView = new ModelView("members");
           modelView.getModel().put("members", members);
   	      return modelView;
      	}
   }
   ```

4. ๊ธฐ๋ฅ Controller๋ฅผ ํตํด ์ ๋ฌ๋ฐ์ View์ ๋ผ๋ฆฌ์  ์ด๋ฆ์ผ๋ก View์ **๋ฌผ๋ฆฌ์  ์์น์ ์ด๋ฆ** ์ฒ๋ฆฌ (View ์ด๋ฆ ์ค๋ณต ์ ๊ฑฐ๋ฅผ ์ํจ.)

   ```java
   ... // Front Controller Code ์ค View ๋ฌผ๋ฆฌ์  ์ด๋ฆ๊ณผ ์์น ๋ฐํ.
   private MyView viewResolver(String viewName) {
       return new MyView("/WEB-INF/views/" + viewName + ".jsp");
   }
   ```

5. Front Controller์์ Request(์์ฒญ)์ผ๋ก ์ ๋ฌ๋ฐ์ ๊ฐ๋ค์ Model๋ก ๋ง๋ค์ด View๋ก ์ ๋ฌํ๋๋ก ๊ตฌํ.


### Ver 4. Front Controller -  Controller ์ฝ๋ ๊ฐ์ํ

1. ๊ฐ Controller์ Parameter๋ก Model์ ๋ฃ์ด ์ฝ๋๋ฅผ ๊ฐ์ํํ๋ค.

   ```java
   public interface ControllerV4 {
       String process(Map<String, String> paramMap, Map<String, Object> model);
   }
   ```

2. Parameter์ model์ ๋ฃ์์ผ๋ก์จ Controller์์ ModelView ๊ด๋ จ ์ฝ๋๋ฅผ ์ ๊ฑฐํ๊ณ  ๋จ์ View Name๋ง ๋ฐํํ๋๋ก ์ค์ 

   ```java
   // MemberListControllerV4 ์ฝ๋ ์ค 
   public String process(Map<String, String> paramMap, Map<String, Object> model) {
       List<Member> members = memberRepository.findAll();
       model.put("members", members);
   	  // ModelView modelView = new ModelView("members"); //์ ๊ฑฐ
   		// modelView.getModel().put("members", members); //์ ๊ฑฐ
       return "members";
   }
   ```

3. ์ฆ, ControllerV3์ ๋ค๋ฅด๊ฒ Model์ ControllerV4์ ์ธ์๋ก ์ฌ์ฉํ์ฌ ModelView์ ๋ํ ์ฝ๋๋ฅผ ์ค์ด๊ณ  ๊ฐ Controller์ ์ฝ๋๋ฅผ ๊ฐ์ํ.


### Spring MVC

#### DispatcherServlet

- DispatcherServlet์ Spring MVC์์ Front Controller ์ญํ ์ ์ํํ๋ค.
- HttpServlet์ ์์๋ฐ์ ์ฌ์ฉํ๋ฉฐ Servlet์ผ๋ก ๋์ํ๋ค.
  - `DispatcherServlet` -> FrameworkServlet -> HttpServletBean -> `HttpServlet`
- Spring Boot๋ DispatcherServlet์ ์๋ธ๋ฆฟ์ผ๋ก ์๋์ผ๋ก ๋ฑ๋กํ๊ณ  ๋ชจ๋  ๊ฒฝ๋ก์ ๋ํด ๋งตํํ๋ค.
- `doDispatch()`์ ์ญํ ์ด ๊ฐ์ฅ ์ค์ํ๋ค.

#### HandlerMapping, HandlerAdapter

- HandlerMapping ์์ Controller๋ฅผ ์ฐพ๋๋ค. 
  - Spring Bean Name์ผ๋ก Handler ํ์ > BeanNameUrlHandlerMapping
  - `Bean Name์ผ๋ก ํ์ํ์ฌ Handler(OldController)  ๋ฐํ`
- HandlerMapping์์ ์ฐพ์ Handler๋ฅผ ์คํํ  HandlerAdapter๊ฐ ํ์
  - Controller Interface(@Controller๊ฐ ์๋) ์ฒ๋ฆฌ > SimpleControllerHandlerAdapter
  - `SimpleControllerHandlerAdapter๊ฐ Handler(OldController)๋ฅผ ๋ด๋ถ์์ ์คํ ํ ๊ฒฐ๊ณผ ๋ฐํ.`

#### View Resolver

- Spring Boot์์ `InternalResourceViewResolver`๋ผ๋ View Resolver๋ฅผ ์๋์ผ๋ก ๋ฑ๋ก
- resouces>apllication.properties ํ์ผ  ์์ ์๋ ์ค์  ์ ๋ณด๋ฅผ ํตํด  ๋ฑ๋ก
  - `spring.mvc.view.prefix`
  - `spring.mvc.view.suffix`



### Ver 1. Spring MVC 

---
## ๐ Project 2
<br>

### ๐ ํ๋ก์ ํธ ์ค์  ์ ๋ณด
- Gradle Project
- Java 8
- Spring Boot 2.6.3
- Jar
- Dependency
  - Spring Web
  - Thymeleaf
  - Lombok

<br>

### ํ๋ก์ ํธ ๊ธฐ๋ฅ 1 - ํ์ ๊ด๋ฆฌ API ๊ตฌํ

- Request Mapiing API 
  - ํ์ ๋ชฉ๋ก ์กฐํ 
    -  GET : `/users`
  - ํ์ ๋ฑ๋ก
    - POST : `/users`
  - ํ์ ์กฐํ
    - GET : `/users/{userId}`
  - ํ์ ์์ 
    - PATCH : `/users/{userId}`
  - ํ์ ์ญ์ 
    - DELETE  : `/users/{userId}`

---

## ๐ Project 3

<br>

### ๐ ํ๋ก์ ํธ ์ค์  ์ ๋ณด

- Gradle Project
- Java 8
- Spring Boot 2.6.3
- Jar
- Dependency
  - Spring Web
  - Thymeleaf
  - Lombok

<br>

### ๐ฏ ํ๋ก์ ํธ ์๊ตฌ ์ฌํญ 

- **์ํ ๋๋ฉ์ธ**
  - ์ํ ID
  - ์ํ๋ช
  - ๊ฐ๊ฒฉ
  - ์๋
- **์ํ ๊ด๋ฆฌ ํ์ ๊ธฐ๋ฅ**
  - ๋ชฉ๋ก ์กฐํ
  - ์ํ ์์ธ ์ ๋ณด ์กฐํ
  - ์ํ ๋ฑ๋ก
  - ์ํ ์์ 
  - ์ํ ์ญ์ 
