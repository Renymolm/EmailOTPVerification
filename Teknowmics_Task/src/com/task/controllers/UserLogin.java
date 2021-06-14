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
import com.task.utils.Utils;

/**
 * Servlet implementation class UserLogin
 */
@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao ;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLogin() {
		super();

	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * // TODO Auto-generated method stub 
		 * doGet(request, response);
		 */



		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			User user = userDao.getUser(username);

			if (user != null && user.isOtpVerified() && user.getPassword().equals(password) ) {
				printWriter.print("<html>");
				printWriter.print("<body>");
				printWriter.print("<h1>Login success !!</h1>");

				printWriter.print("</body>");
				printWriter.print("</html>");
			}else if (user == null || !user.isOtpVerified() ) {
				String otp = Utils.generateOTP(6);

				if (user == null) {
					
					user = new User();
					user.setUsername(username);
					user.setPassword(password);
					user.setOtp(otp);

					userDao.createUser(user);
				}else {
					
					user.setPassword(password);
					user.setOtp(otp);
					
					userDao.updateOTPCreatedTime(user);
				}
				
				
				
				boolean emailSendStatus = Utils.sendOTPEmail3(username,otp);
				if (emailSendStatus) {
					printWriter.print("<html>");
					printWriter.print("<body>");
					printWriter.print("<h3>Enter OTP send to email</h3>");
					printWriter.print("<form action=\"OTPVerifierServlet\" method=\"post\"> ");
					printWriter.print("<table style=\"with: 100%\"> ");
					printWriter.print("<tr> <td>OTP</td> <td><input type=\"text\" name=\"otp\" /></td></tr>");
					printWriter.print("<tr>  <td><input type=\"hidden\" name=\"username\"  value='"+username+"'/></td></tr>");
					printWriter.print("</table> ");
					printWriter.print("<input type=\"submit\" value=\"Done\" /> ");
					printWriter.print("</form> ");
					printWriter.print("</body>");
					printWriter.print("</html>");
				} else {

					printWriter.print("<html>");
					printWriter.print("<body>");
					printWriter.print("<h3>Unable to send email.Please try again !</h3>");

					printWriter.print("</body>");
					printWriter.print("</html>");

				} 
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
			printWriter.print("<html>");
			printWriter.print("<body>");
			printWriter.print("<h1>Error</h1>");

			printWriter.print("</body>");
			printWriter.print("</html>");

		}

		finally {
			printWriter.close();
		}

	}

}
