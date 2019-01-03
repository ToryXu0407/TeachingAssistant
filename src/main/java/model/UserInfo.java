package model;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
 * @Author: toryxu
 * @Date: 2018/12/24 0024 22:13
 * @Version 1.0
 * @description Vue登录页面demo信息对象实体
 * @memo 备注信息
 */
@Setter
@Getter
public class UserInfo {
    @NotNull(message="用户id不允许为空")
    private int userid;

    @NotNull(message="用户名不允许为空")
    private String username;

    @NotNull(message="密码不允许为空")
    private String password;

    private int type;

    private String createTime;

    private String headImage;

    private String isTeacher;

}

