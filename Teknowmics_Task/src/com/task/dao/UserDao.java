package com.task.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.task.objects.User;

public class UserDao {

	public int createUser(User user) throws SQLException, ClassNotFoundException {
		int result = 0 ;

		String INSERT_USERS_SQL = "INSERT INTO user" +
				"  ( username, password, otp,otp_createdon,otp_verified) VALUES " +
				" (?, ?, ?,?,? );";

		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(new java.util.Date().getTime());

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root", "admin");


			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL) ;

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getOtp()); 
			preparedStatement.setTimestamp(4, currentDateTime);
			preparedStatement.setBoolean(5, false);

			System.out.println(preparedStatement);

			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			throw e;
		}

		return result;

	}

	public User getUser(String username) throws SQLException, ClassNotFoundException {

		String SELECT_USER_SQL = "SELECT  `username`, `password`, `id`, `otp`, `otp_createdon`,`otp_verified` FROM  `user` WHERE `username` = ?;";

		User user = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root", "admin");


			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL) ;

			preparedStatement.setString(1, username);


			System.out.println(preparedStatement);

			ResultSet resultSet = preparedStatement.executeQuery();
			 
			while(resultSet.next())
			{
				resultSet.absolute(1); 
				System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(4));  
				user = new User();
				user.setUsername(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setId(resultSet.getInt(3));
				user.setOtp( resultSet.getString(4));
				user.setOtpCreatedOn(resultSet.getDate(5));
				user.setOtpVerified(resultSet.getBoolean(6));
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			throw e;
		}


		return user;

	}
	
	public int updateOTPVerified(User user) throws SQLException, ClassNotFoundException {
		int result = 0 ;

		String UPDATE_USERS_SQL = "UPDATE  `user` SET  `otp_verified` = 1 WHERE `username` = ?;";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root", "admin");


			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL) ;

			preparedStatement.setString(1, user.getUsername());
			 
			System.out.println(preparedStatement);

			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			throw e;
		}

		return result;

	}

	public int updateOTPCreatedTime(User user) throws SQLException, ClassNotFoundException {
		int result = 0 ;

		String UPDATE_USERS_SQL = "UPDATE  `user` SET  `otp_createdon` = ?,`otp` = ?,password = ? WHERE `username` = ?;";
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root", "admin");


			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL) ;
			preparedStatement.setTimestamp(1, currentDateTime);
			preparedStatement.setString(2, user.getOtp());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getUsername());
			 
			System.out.println(preparedStatement);

			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			throw e;
		}

		return result;

	}


}
