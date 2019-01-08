package model;

/**
 * @Author: toryxu
 * @Date: 2018/12/24 0024 22:13
 * @Version 1.0
 * @description 响应结果生成工厂类
 * @memo 无备注说明
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buidResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildSuccessArticleResult(Object data,long totalPage) {
        return buidArticleResult(ResultCode.SUCCESS, "成功", data,totalPage);
    }

    public static Result buildFailResult(String message) {
        return buidResult(ResultCode.FAIL, message, null);
    }

    public static Result buidResult(ResultCode resultCode, String message, Object data) {
        return buidResult(resultCode.code, message, data);
    }

    public static Result buidResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }

    public static Result buidArticleResult(ResultCode resultCode, String message, Object data,long totalPage) {
        return buidArticleResult(resultCode.code, message, data,totalPage);
    }

    public static Result buidArticleResult(int resultCode, String message, Object data,long totalPage) {
        return new Result(resultCode, message, data,totalPage);
    }
}
