package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/3 0003 14:29
 * @Version 1.0
 * 帖子
 */
@Setter
@Getter
public class Article {

    private int articleId;

    private int userId;

    private String createTime;

    private String updateTime;

    private String isSticky;

    private String label;

    private String content;

    private String brief;

    private String username;

    private String headImage;
}
