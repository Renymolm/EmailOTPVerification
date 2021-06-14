package com.task.objects;

import java.util.Date;

public class User {

	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

	private int id ;
	private String username;
	private String password;
	private String otp;
	private Date otpCreatedOn;
	private boolean otpVerified ;

	
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public boolean isOtpVerified() {
		return otpVerified;
	}
	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Date getOtpCreatedOn() {
		return otpCreatedOn;
	}
	public void setOtpCreatedOn(Date otpCreatedOn) {
		this.otpCreatedOn = otpCreatedOn;
	}


	public boolean isOTPExpired() {
		 
		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = this.otpCreatedOn.getTime();

		if (otpRequestedTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis) {
			// OTP expires
			return true;
		}


		return false;
	}

}
