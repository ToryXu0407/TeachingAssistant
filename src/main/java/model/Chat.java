package model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/2/13 0013 12:23
 * @Version 1.0
 */
@Setter
@Getter
public class Chat {

    int id;

    int chatRoomId;

    int userId;

    String nickname;

    String content;

    String isTeacher;

    String createTime;


}
