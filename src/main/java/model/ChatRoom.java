package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/2/13 0013 15:15
 * @Version 1.0
 */
@Setter
@Getter
public class ChatRoom {
    private int id;

    private int teacherId;

    private String courseName;

    private String startTime;

    private String endTime;

    private int chatsCount;

    private String createTime;

    private String nickname;

    private String username;

    private String headImage;

    private String videoUrl;
}
