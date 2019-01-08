package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2018/12/24 0024 22:11
 * @Version 1.0
 * @memo 控制Result权限，构建结果Result对象统一使用com.javalsj.blog.vo.ResultFactory工厂类来创建
 */
@Getter
@Setter
public class Result {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应提示信息
     */
    private String message;
    /**
     * 响应结果对象
     */
    private Object data;
    /**
     * 帖子总页数
     * @param code
     * @param message
     * @param data
     */
    private long totalPage;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    Result(int code, String message, Object data,long totalPage) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.totalPage = totalPage;
    }


}

