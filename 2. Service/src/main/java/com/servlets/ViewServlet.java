package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.RequestViewHelper;

@WebServlet("*.test")
public class ViewServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println(request.getRequestURI());
		
		String nextView = RequestViewHelper.process(request);
		request.getRequestDispatcher(nextView).forward(request, response);
		
	}

}
