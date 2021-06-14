package com.task.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.task.dao.UserDao;
import com.task.objects.User;

/**
 * Servlet implementation class OTPVerifierServlet
 */
@WebServlet("/OTPVerifierServlet")
public class OTPVerifierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao ;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPVerifierServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String username = request.getParameter("username");
		String otp = request.getParameter("otp");
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		String message = "";
		try {
			User user = userDao.getUser(username);
			System.out.println("User , otp :"+username+","+otp);
			System.out.println("OTP in db:"+user.getOtp());
			if(!otp.equals(user.getOtp())) {
				message = "Invalid OTP" ;
			}else {
				if(user.isOTPExpired()) {
					message = "Expired OTP" ;
				}else {
					message = "Login success !!" ;
					userDao.updateOTPVerified(user);
				}

			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			message = "Error" ;

		} catch (SQLException e) {

			e.printStackTrace();
			message = "Error" ;
		}


		printWriter.print("<html>");
		printWriter.print("<body>");
		printWriter.print("<h1>"+message+"</h1>");

		printWriter.print("</body>");
		printWriter.print("</html>");
		printWriter.close();




	}

}
