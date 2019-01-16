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
    //文章编号
    private int id;

    //用户编号
    private int userId;

    //创建时间
    private String createTime;

    //修改时间
    private String updateTime;

    //是否加精
    private String isSticky;

    //标题
    private String label;

    //内容
    private String content;

    //简介
    private String brief;

    //用户名
    private String username;

    //用户头像
    private String headImage;

    //查看数目
    private int viewCount;

    //回帖数
    private int commentCount;

    //点赞数
    private int voteCount;

    //昵称
    private String nickname;

    //登陆用户是否已经点赞
    private Boolean isVoted;
}
