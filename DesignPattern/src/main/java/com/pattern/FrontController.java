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
	//����4 �ʵ庯���� map����
	//map���� ���������� ������ Ÿ���� ��ü�� ����
	//��� Ÿ�� ���尡��?
	// *.do �� Ű������ ���
	//new ~~~ ��ü�� ������ ���
	//map<keyŸ��, vale Ÿ��>
	//key Ÿ�� -> String���� ���� -> ��??? ->>> ������� ��û�� ������ "/insert.do"�� ���� �ĺ����� Ȱ���ؼ� ���� -> ���� ��Ʈ��Ÿ���̴ϱ�
	//"/insert.do" ��û�� ������  InsertService��ü�� ������ �Ǿ���Ѵ�!
	//"/update.do" ��û�� ������  UpdateService��ü�� ������ �Ǿ���Ѵ�!
	private Map<String, Command> map;
	
	@Override
	//���� 4-1
	//init() �޼ҵ�� ���ʿ� �ѹ��� �����̵ȴ� ->service()�� ����ɶ� ��ü�� �ѹ��� ����� �� �ְ� �ϴ� �޼ҵ�!(�޸� ����) ->> ��ü�� �ѹ��� ����� -> init()�� ������ �����Ҷ����� ��� ��ü�� ���� ����
	public void init() throws ServletException {
		map = new HashMap<String, Command>(); //- >> map�̶�� �������̽��� �����ϱ� ���ؼ� hashmap�� map�� ��´�. -->> map�� Ű�� ������ �����ϱ� ���ؼ��̴�!
		// ->>>> map�̶�� �������̽������� ��ü�� ������ �� ���� ������ hashmap�� �̿��ؼ� ��ü�� �����ؼ� �����Ѵ�.
		//map.put("��û�ĺ���", �ĺ����� ������ ��ü);
		map.put("/insert.do", new InsertService()); //->> Ű���� ������ �����Ѵ�.
		map.put("/update.do", new UpdateService());
		map.put("/delete.do", new DeleteService());
		map.put("/select.do", new SelectService());
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����1
		//FrontController ����
		// -> ������� ��� ��û�� �� ������ ������ �� �ְ� �ϴ� ���� 
		// -> URLMapping�� ��θ� " *.do" �� �����ؾ� �Ѵ�.
		// -> ������(html, jsp)������ ��� �䫊���� .do �� �ٿ��� �ϳ��� �������� ���� �ȴ�!
		//����
		// - > �Ѱ��� �߾�����ȭ �Ǹ鼭 ������ ������� �ϳ��� ����� ������ �߻��ϸ� ��� ����� ����� �� ���� �ȴ�.
		//����3
		// -> ������ �����ϱ� ���� ��ɺ��� �Ϲ�Ŭ�������Ϸ� �и��� ��Ų��!
		
		//�Ϲ�Ŭ������ ������ ����
		// -> HttpServlet�� ��ӹ޾Ҵ����� ���� ����
		//->> ���������� ������ �޸𸮸� ����ϴ� �Ϳ� ���� �����̴�!
		//������ ��Ĺ�� ������ ������ ������ �޸𸮸� ��� ����Ѵ�
		//�Ϲ�Ŭ������ ��ü�� ������ ���� ������ �޸𸮸� ����Ѵ�.
		
		//����3 -1
		//�������̽��� Ȱ���ؼ� �Ϲ�Ŭ������ �����Ѵ�!
		// -> ���Ŀ� ������� ���񽺿� ���ؼ� ������ �޼ҵ�� ������ �����Ѵ�!
		// ->> �̰��� commandPattern �̶�� �Ѵ�
		//����3-2
		//Command ���� : ������� ��û�� ���� ó���� �� �ִ� �Ϲ�Ŭ�������� ����� �޼ҵ�� ������ �� �ֵ��� �ϴ� ����
		// -> execute(HttpServletRequest, HttpServletResponse) �߻� �޼ҵ� ����(�ΰ��� �Ű������� �޴´�) -> ������ �׻� �ִ�����!
		// -> �Ϲ� Ŭ������ implements Ű���带 �̿��ؼ� �������̽��� ����
		
		
		//����2
		//reqURI() : /DesignPattern/insert.do
		//contextPath() : /DesingPattern
		//���ϴ°� insert.do�̱� ������ �տ� ������ ������ ���־��Ѵ�!
		//-> substring() �� ����Ѵ�(�ε����ϴ°�!)
		String reqURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		//String command = reqURI.substring(contextPath.length());
		
		//�̷��� ���� �ٷ� �������� ����� �ִ� ->> �̰ɷ� �������!
		//command : /insert.do
		//���ϰ��(���� ���ε� ���)�� ��ȯ!
		String command = request.getServletPath();
		
		
		//command : /insert.do �� ���´�!
		
		
		
		//Command ���� : ������� ��û�� ���� ó���� �� �ִ� �Ϲ�Ŭ�������� ����� �޼ҵ�� ������ �� �ֵ��� �ϴ� ����
				// -> execute(HttpServletRequest, HttpServletResponse) �߻� �޼ҵ� ����(�ΰ��� �Ű������� �޴´�) -> ������ �׻� �ִ�����!
				// -> �Ϲ� Ŭ������ implements Ű���带 �̿��ؼ� �������̽��� ����
		
		//����2 �۵��ϳ� ���		
		System.out.println("��û�ĺ��� >> " + command);
		
//		if(command.equals("/insert.do")) {
//			//������ �߰� ����
//			Command insert = new InsertService(); //����3 ��� - >�������̽��� �������� Ŭ�������� ��ü �����Ͽ� �޼ҵ� ���!! 
//			insert.execute(request, response);
//		}else if(command.equals("/update.do")) {
//			//������ ���� ����
////			UpdateService update = new UpdateService();
//			Command update = new UpdateService(); //����4 �������� ����! ->> �ϳ��� Ÿ������ �����Ͽ� ��ü�� �����Ѵ� ->> Command�� �����ް��ֱ⶧���� �ϳ��� ��ü�� ���ϰ���!
//			update.execute(request, response);
//		}else if(command.equals("/delete.do")) {
//			//������ ���� ����
//			Command delete = new DeleteService();
//			delete.execute(request, response);
//		}else if(command.equals("/select.do")) {
//			//������ ��ȸ ����
//			Command select = new SelectService();
//			select.execute(request, response);
//		}
		
		//���� 4-2 map�� �̿��ϱ�
		Command com = map.get(command); //-> ���⼭ command�� ����ڿ��� ��û���� �ĺ��� �����̴�!(79��° ��) ->> map.get(command); �� map�� value�� �ִ� ��ü�̴�!
		//95��° �ٿ��� ������ �ٲ���ٰ� �����ϸ� ����.
		//Command com(=insert) = map.get(command);(=new InsertService()) �Ȱ���
		com.execute(request, response);
		
		
	}

}
