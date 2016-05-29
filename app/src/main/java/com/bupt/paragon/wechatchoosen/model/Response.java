package com.bupt.paragon.wechatchoosen.model;

/**
 * Created by Paragon on 2016/5/27.
 */
public class Response {
    private String reason;
    private Result result;
    private int error_code;
    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
