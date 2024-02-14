package com.student_serve.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private int code;

    private String msg;


    private T data;
    public static <T> Result<T> success(){
        return new Result<>(1,"success",null);
    }

    public static <T> Result<T> success(int code,String msg,T data){
        return new Result<>(code,msg,data);
    }

    public static <T> Result<T> error(){
        return new Result<>(0,"error",null);
    }

    public static <T> Result<T> error(int code,String msg,T data){
        return new Result<>(code,msg,data);
    }
}
