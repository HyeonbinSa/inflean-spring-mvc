## Inflean - Spring MVC  학습

---

### 공부 순서

1. Servlet을 이용한 Web 
2. JSP의 사용과 MVC 패턴 적용
3. Front Controller 적용

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
