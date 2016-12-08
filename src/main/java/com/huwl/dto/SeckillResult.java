package com.huwl.dto;

/**
 * 所有ajax请求返回类型，封装json结果
 * @author zzh
 *
 * @param <T>
 */
public class SeckillResult<T> {
	private boolean success;	//ajax请求是否成功
	private T data;	//返回的数据
	private String error;	//错误信息
	
	public SeckillResult(boolean success, String error) {	//错误情况
		this.success = success;
		this.error = error;
	}

	public SeckillResult(boolean success, T data) {		//成功情况
		this.success = success;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
