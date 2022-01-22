package com.pattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//순서4 필드변수로 map생성
	//map으로 다형성으로 통일한 타입을 객체를 저장
	//모든 타입 저장가능?
	// *.do 를 키값으로 사용
	//new ~~~ 객체를 벨류로 사용
	//map<key타입, vale 타입>
	//key 타입 -> String으로 지정 -> 왜??? ->>> 사용자의 요청이 들어오면 "/insert.do"와 같은 식별값을 활용해서 구별 -> 저게 스트링타입이니까
	//"/insert.do" 요청이 들어오면  InsertService객체가 생성이 되어야한다!
	//"/update.do" 요청이 들어오면  UpdateService객체가 생성이 되어야한다!
	private Map<String, Command> map;
	
	@Override
	//순서 4-1
	//init() 메소드는 최초에 한번만 실행이된다 ->service()가 실행될때 객체가 한번만 실행될 수 있게 하는 메소드!(메모리 절약) ->> 객체를 한번만 만든다 -> init()이 없으면 실행할때마다 계속 객체를 새로 생성
	public void init() throws ServletException {
		map = new HashMap<String, Command>(); //- >> map이라는 인터페이스를 구현하기 위해서 hashmap을 map에 담는다. -->> map에 키와 벨류를 저장하기 위해서이다!
		// ->>>> map이라는 인터페이스에서는 객체를 생성할 수 없기 때문에 hashmap을 이용해서 객체를 생성해서 저장한다.
		//map.put("요청식별값", 식별값과 연관된 객체);
		map.put("/insert.do", new InsertService()); //->> 키값과 벨류를 저장한다.
		map.put("/update.do", new UpdateService());
		map.put("/delete.do", new DeleteService());
		map.put("/select.do", new SelectService());
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//순서1
		//FrontController 패턴
		// -> 사용자의 모든 요청을 한 곳으로 전송할 수 있게 하는 구조 
		// -> URLMapping의 경로를 " *.do" 로 설정해야 한다.
		// -> 페이지(html, jsp)에서는 모든 요쳥에는 .do 를 붙여야 하나의 서블릿으로 오게 된다!
		//단점
		// - > 한곳에 중앙집중화 되면서 로직이 길어지고 하나의 기능이 문제가 발생하면 모든 기능을 사용할 수 없게 된다.
		//순서3
		// -> 단점을 보완하기 위해 기능별로 일반클래스파일로 분리를 시킨다!
		
		//일반클래스와 서블릿의 차이
		// -> HttpServlet을 상속받았는지에 대한 차이
		//->> 지속적으로 서버의 메모리를 사용하는 것에 대한 차이이다!
		//서블릿은 톰캣이 꺼지기 전까지 서버의 메모리를 계속 사용한다
		//일반클래스는 객체를 생성할 때만 서버의 메모리를 사용한다.
		
		//순서3 -1
		//인터페이스를 활용해서 일반클래스를 구현한다!
		// -> 이후에 만들어질 서비스에 대해서 동일한 메소드로 구현을 강제한다!
		// ->> 이것을 commandPattern 이라고 한다
		//순서3-2
		//Command 패턴 : 사용자의 요청에 따라 처리할 수 있는 일반클래스들의 공통된 메소드로 구현될 수 있도록 하는 구조
		// -> execute(HttpServletRequest, HttpServletResponse) 추상 메소드 구현(두개의 매개변수를 받는다) -> 서블릿에 항상 있던거임!
		// -> 일반 클래스에 implements 키워드를 이용해서 인터페이스를 구현
		
		
		//순서2
		//reqURI() : /DesignPattern/insert.do
		//contextPath() : /DesingPattern
		//원하는건 insert.do이기 때문에 앞에 디자인 패턴을 없애야한다!
		//-> substring() 을 사용한다(인덱싱하는것!)
		String reqURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		//String command = reqURI.substring(contextPath.length());
		
		//이렇게 값을 바로 가져오는 방법도 있다 ->> 이걸로 사용하자!
		//command : /insert.do
		//파일경로(서블릿 맵핑된 경로)값 반환!
		String command = request.getServletPath();
		
		
		//command : /insert.do 만 남는다!
		
		
		
		//Command 패턴 : 사용자의 요청에 따라 처리할 수 있는 일반클래스들의 공통된 메소드로 구현될 수 있도록 하는 구조
				// -> execute(HttpServletRequest, HttpServletResponse) 추상 메소드 구현(두개의 매개변수를 받는다) -> 서블릿에 항상 있던거임!
				// -> 일반 클래스에 implements 키워드를 이용해서 인터페이스를 구현
		
		//순서2 작동하나 출력		
		System.out.println("요청식별값 >> " + command);
		
//		if(command.equals("/insert.do")) {
//			//데이터 추가 로직
//			Command insert = new InsertService(); //순서3 출력 - >인터페이스로 구현받은 클래스들의 객체 생성하여 메소드 사용!! 
//			insert.execute(request, response);
//		}else if(command.equals("/update.do")) {
//			//데이터 수정 로직
////			UpdateService update = new UpdateService();
//			Command update = new UpdateService(); //순서4 다형성을 적용! ->> 하나의 타입으로 통일하여 객체를 생성한다 ->> Command를 구현받고있기때문에 하나의 객체로 통일가능!
//			update.execute(request, response);
//		}else if(command.equals("/delete.do")) {
//			//데이터 삭제 로직
//			Command delete = new DeleteService();
//			delete.execute(request, response);
//		}else if(command.equals("/select.do")) {
//			//데이터 조회 로직
//			Command select = new SelectService();
//			select.execute(request, response);
//		}
		
		//순서 4-2 map을 이용하기
		Command com = map.get(command); //-> 여기서 command는 사용자에게 요청받은 식별값 변수이다!(79번째 줄) ->> map.get(command); 는 map의 value에 있는 객체이다!
		//95번째 줄에서 변수만 바뀌었다고 생각하면 쉽다.
		//Command com(=insert) = map.get(command);(=new InsertService()) 똑같음
		com.execute(request, response);
		
		
	}

}
