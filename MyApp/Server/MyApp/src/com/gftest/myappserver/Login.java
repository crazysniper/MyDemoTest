package com.gftest.myappserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		System.out.println("action=" + action);
		System.out.println("name=" + name);
		System.out.println("password=" + password);

		PrintWriter printWriter = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if ("get".equals(action)) {
			jsonObject.put("name", name);
			jsonObject.put("password", password);
			jsonObject.put("id", "1");
			jsonObject.put("ret", "1");
		} else {

		}
		printWriter.write(jsonObject.toString());

		System.out.println("jsonObject=" + jsonObject.toString());

		printWriter.flush();
		printWriter.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
