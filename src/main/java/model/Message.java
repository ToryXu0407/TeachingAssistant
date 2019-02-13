package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: toryxu
 * @Date: 2019/2/11 0011 14:04
 * @Version 1.0
 */
@Setter
@Getter
public class Message {

    private int id;

    private int chatRoomId;

    // 发送者
    private UserInfo from;
    // 发送者名称
    private String nickname;

    // 接收者
    private UserInfo to;
    // 发送的文本
    private String text;
    // 发送日期
    private String date;

    private int isSelf;

    private String message;
}
