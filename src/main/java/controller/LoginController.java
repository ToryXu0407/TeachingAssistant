package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import model.Result;
import model.ResultFactory;
import model.UserInfo;

@PermissionOwn(name="login")
public class LoginController extends BaseController {
    public static final String SALT="TeachingAssistant";

	@UnCheckLogin
	public void login() {
		render("login.html");
	}


	@UnCheckLogin
	public void Toregister() {
        redirect("/register/register");
	}

	@UnCheckLogin
	public void loginSubmit() {
		Result result;
		try {
			UserInfo UserInfo = getBean(UserInfo.class,"");
			String username = UserInfo.getUsername();
			String password = UserInfo.getPassword();
			if (StrKit.isBlank(username)) {
				throw new RuntimeException("用户名不能为空");
			}
			if (StrKit.isBlank(password)) {
				throw new RuntimeException("密码不能为空");
			}
			//登录检测
			Record userRecord = Db.use("ta").findFirst("select * from ta_user where username = ? and password = ?", username,password);
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
			result = ResultFactory.buildSuccessResult(UserInfo);
			//redirect("/student");
		} catch (Exception e) {
			LOG.error("出错啦", e);
			setAttr("error", e.getMessage());
			result = ResultFactory.buildFailResult(e.getMessage());
			//renderVelocity("login.html");
		}
		renderJson(result);
	}
	@UnCheckLogin
	public void getLoggedInfo() {
		Result result;
		int userid;
		String username;
		if(getSessionAttr(PermissionChecker.USER_ID)!=null&&getSessionAttr(PermissionChecker.USER_USERNAME)!=null){
			 userid = getSessionAttr(PermissionChecker.USER_ID);
			username = getSessionAttr(PermissionChecker.USER_USERNAME);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserid(userid);
			userInfo.setUsername(username);
			result = ResultFactory.buildSuccessResult(userInfo);
		}
		else{
            result = ResultFactory.buildSuccessResult(null);
        }
        renderJson(result);
	}
	@UnCheckLogin
	public void logout(){
		removeSessionAttr(PermissionChecker.USER_ID);
		removeSessionAttr(PermissionChecker.USER_USERNAME);
		redirect("/login");
	}

}
