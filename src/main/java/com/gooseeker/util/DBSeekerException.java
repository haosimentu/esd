package com.gooseeker.util;

import org.springframework.dao.DataAccessException;
/**
 * 自定义数据库异常，数据库操作失败时抛出
 * @author ysite
 *
 */
public class DBSeekerException extends DataAccessException {
	private static final long serialVersionUID = 1L;
	
	private int errorCode = -1;
	
	public DBSeekerException(String msg) {
		super(msg);
	}
	
	public DBSeekerException(String msg,int error) {
		super(msg);
		this.errorCode = error;
	}
	
	public DBSeekerException(String msg, Throwable cause)
	{
		super(msg,cause);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("errorCode : ");
		builder.append(errorCode);
		builder.append("; message :");
		builder.append(this.getMessage());
		return builder.toString();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
