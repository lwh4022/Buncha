package com.buncha.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Result {

	public static final String SUCCESS_MESSAGE = "성공";
    public static final String SERVER_ERROR_MESSAGE = "실패";
    
    private HttpStatus statusCode;
    private String message;
    private Object data;
    
    public Result(){}

    public static Result successInstance(){
        return new Result().success();
    }

    public Result success(){
        statusCode = HttpStatus.OK;
        message = SUCCESS_MESSAGE;
        return this;
    }

    public Result fail(){
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        message = SERVER_ERROR_MESSAGE;
        return this;
    }

    public Result setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public Result setData(Object data) {
    	this.data = data;
    	return this;
    }
    
    public int getStatusCode() {
        return statusCode.value();
    }
}
