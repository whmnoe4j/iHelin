package me.ianhe.model;

/**
 * @author iHelin
 * @create 2017-02-15 19:24
 */
public class Result {

    public static final int SUCCESS_STATUS = 0; // 成功
    public static final int FAILURE_STATUS = 1; // 错误

    private String message;
    private int status;
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
