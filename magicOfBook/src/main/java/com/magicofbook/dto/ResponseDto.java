package com.magicofbook.dto;

public class ResponseDto {
	
	private String msg;
	private Object data;
	public ResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseDto(String msg, Object data) {
		super();
		this.msg = msg;
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResponseDto [msg=" + msg + ", data=" + data + "]";
	}
	
	

}
