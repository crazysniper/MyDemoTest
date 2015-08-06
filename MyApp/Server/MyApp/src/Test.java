import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.org.mozilla.javascript.internal.json.JsonParser;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Test() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		PrintWriter out = response.getWriter();
		List<Person> persons = getListPerson();
		StringBuffer sb = new StringBuffer();
		sb.append('{');
		sb.append("\"data\":[");
		for (Person person : persons) {
			sb.append('{').append("\"name\":").append("\"" + person.getName() + "\"").append(",");
			sb.append("\"city\":").append("\"" + person.getCity() + "\"").append(",");
			sb.append("\"age\":").append(person.getAge());
			sb.append('}').append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(']');
		sb.append('}');
		out.write(new String(sb));
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	public static List<Person> getListPerson() {
		List<Person> mLists = new ArrayList<Person>();
		mLists.add(new Person("张三", "北京", 20));
		mLists.add(new Person("李四", "上海", 30));
		mLists.add(new Person("王五", "深圳", 35));
		return mLists;
	}

}
