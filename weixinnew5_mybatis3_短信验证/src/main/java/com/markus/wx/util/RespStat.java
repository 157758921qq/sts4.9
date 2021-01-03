package com.markus.wx.util;

public class RespStat {

	/**
	 * 	JSON报文
	 * 状态码
	 * 用于前端判断：
	 * 		200 == 成功
	 * 		400/500  出错
	 * 	Json数据可以写在data中
	 * 	msg = 返给前端的报文信息
	 */
	
	private int code;
	private String msg;
	private String data;
	
	//写有参的就必须要写无参的构造方法
		public RespStat() {
			super();
		}
		
		public RespStat(int code, String msg, String data) {
			super();
			this.code = code;
			this.msg = msg;
			this.data = data;
		}
		
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
	
	public static RespStat build(int i) {
		return new RespStat(i,"后端成功处理前端数据，结果返回给前端，执行成功","meiyou");
	}

	//自定义消息状态
	public static RespStat build(int i, String msg, String data) {
		return new RespStat(i, msg, data);
	}
	
	
	
	
	
	
	
	
}





























