package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/20 0020 23:28
 * @Version 1.0
 * 通知model层
 */
@Setter
@Getter
public class Notice {

    private int id;

    private String name;

    private int courseId;

    private String content;

    private String createTime;

    private String updateTime;

    private String image;
}
