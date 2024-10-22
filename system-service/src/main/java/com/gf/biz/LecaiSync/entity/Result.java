package com.gf.biz.LecaiSync.entity;




public class Result {
    private Integer code;//响应码，1 代表成功; 0 代表失败
    private String msg;  //响应信息 描述字符串
    private Object data; //返回的数据

    public Result(int i, String success, Object o) {
    }

    //增删改 成功响应
    public static Result success(){
        return new Result(0,"success",null);
    }//不需要返回值，null
    //查询 成功响应
    public static Result success(Object data){
        return new Result(0,"success",data);
    }//查询使用，返回数据
    //失败响应
    public static Result error(String msg){
        return new Result(1,msg,null);
    }//返回报错信息
    
    
}
