package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import util.MD5Util;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@PermissionOwn(name="login")
public class LoginController extends BaseController {
    public static final String SALT="TeachingAssistant";

	public void index() {
		render("index.html");
	}
	@PermissionOwn(name="login")
	@UnCheckLogin
	public void login() {
		render("login.html");
	}

	@UnCheckLogin
	public void loginSubmit() {
		try {
			String username = getPara("username").trim();
			String password = getPara("password").trim();
			if (StrKit.isBlank(username)) {
				throw new RuntimeException("用户名不能为空");
			}
			if (StrKit.isBlank(password)) {
				throw new RuntimeException("密码不能为空");
			}

			//登录检测
			Record userRecord = Db.use("ta").findFirst("select * from ta_user where username = ? and pwd = ?", username,password);
			if (userRecord == null) {
				throw new RuntimeException("用户名/密码输入错误");
			}

			setSessionAttr(PermissionChecker.USER_ID, userRecord.get("id"));
			setSessionAttr(PermissionChecker.USER_USERNAME, username);
			//每类用户对应一种权限，其实也没必要，因为每类用户跳转的页面都是不一样的
			//就是怕一种情况用户直接去访问路由，如果没权限限制的话就可以访问其他用户的界面
			String type = userRecord.get("type")+"";
			if(!StrKit.isBlank(type)){
			    Record permission = Db.use("ta").findFirst("select * from ta_user_permission where user_type =? ",type);
				//将用户权限存入session
				setSessionAttr(PermissionChecker.USER_TYPE,permission.get("name"));
			}
			LOG.info("登录成功,username=" + username);

			redirect("/student");
			
		} catch (Exception e) {
			LOG.error("出错啦", e);
			setAttr("error", e.getMessage());
			renderVelocity("login.html");
		}
	}
	@UnCheckLogin
	public void logout(){
		removeSessionAttr(PermissionChecker.USER_ID);
		removeSessionAttr(PermissionChecker.USER_USERNAME);
		redirect("/login");
	}
	
}
