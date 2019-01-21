package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/21 0021 14:51
 * @Version 1.0
 */
@Setter
@Getter
public class Courseware {

    private int id;

    private int courseId;

    private String name;

    private String url;

    private String createTime;

    private String updateTime;
}
