package cc.lingenliu.mhm.backend.document.starter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private int status;

    private String msg;

    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static BaseResponse<Object> ok(){
        return new BaseResponse<>();
    }

    public static<T> BaseResponse<T> ok(T result){
        BaseResponse<T> response = new BaseResponse<>();
        response.result = result;
        return response;
    }

    public static BaseResponse<Object> fail(int status){
        BaseResponse<Object> response = new BaseResponse<>();
        response.status = status;
        return response;
    }

    public static BaseResponse<Object> fail(int status,String msg){
        BaseResponse<Object> response = new BaseResponse<>();
        response.status = status;
        response.msg = msg;
        return response;
    }

    public boolean isResultSuccess(){
        return this.status == 0;
    }
}
