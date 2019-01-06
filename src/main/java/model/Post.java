package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/4 0004 17:00
 * @Version 1.0
 * 回帖内容
 */
@Getter
@Setter
public class Post {

    private int postId;

    private int articleId;

    private int userId;

    private int parentUserId;

    private String createTime;

    private String updateTime;

    private String content;
}
