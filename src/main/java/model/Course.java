package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/18 0018 13:38
 * @Version 1.0
 */
@Setter
@Getter
public class Course {

    private int id;

    private String name;

    private String description;

    private String createTime;

    private int viewCount;

    private String image;
}
