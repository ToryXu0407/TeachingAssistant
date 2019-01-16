package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/4 0004 17:00
 * @Version 1.0
 * 回帖内容
 */
@Getter
@Setter
public class Post {

    private int id;

    private int articleId;

    private int userId;
    //被回帖者ID
    private int toUserId;

    private String toUserNickName;

    private int parentPostId;

    private String createTime;

    private String updateTime;

    private String content;

    //用户名
    private String username;

    //用户头像
    private String headImage;

    //楼数
    private int position;

    //昵称
    private String nickname;

    //该楼的回贴
    private List<Post> replies;

    private int replyNum;
}
