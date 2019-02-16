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
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

@PermissionOwn(name="register")
public class RegisterController extends BaseController {
	public static final Logger LOG=Logger.getLogger(RegisterController.class);
    public static final String SALT="TeachingAssistant";

	public void index() {
		render("index.html");
	}
	@PermissionOwn(name="register")
	@UnCheckLogin
	public void register() {
		render("register.html");
	}


	@UnCheckLogin
	public void RegisterSubmit() {
		Result result;
		try {
			String username = getPara("username").trim();
			String password = getPara("password").trim();
			String nickname = getPara("nickname").trim();
			String isTeacher = getPara("isTeacher").trim();
			if (StrKit.isBlank(username)) {
				throw new RuntimeException("用户名不能为空");
			}
			if (StrKit.isBlank(nickname)) {
				throw new RuntimeException("昵称不能为空");
			}
			if (StrKit.isBlank(password)) {
				throw new RuntimeException("密码不能为空");
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			//身份：0：管理员；1：老师；2：学生,管理员账号不通过注册。
			int idStatus=2;
			Record record = new Record();
			record.set("username",username);
			record.set("nickname",nickname);
			record.set("password",password);
			if(isTeacher.equals("Y"))
				idStatus=1;
			record.set("type",idStatus);
			record.set("create_time", dateString);
			Record record1 = Db.use("ta").findFirst("select * from ta_user where username= ? ",username);
			if(record1!=null)
			    throw new RuntimeException("用户名已存在!");
			record1 = Db.use("ta").findFirst("select * from ta_user where nickname= ? ",nickname);
			if(record1!=null)
				throw new RuntimeException("昵称已存在!");
			Db.use("ta").save("ta_user", record);
			record = Db.use("ta").findFirst("select * from ta_user where username= ? ",username);
			UserInfo userInfo = new UserInfo();
			userInfo.setNickname(record.getStr("nickname"));
			userInfo.setUsername(record.getStr("username"));
			userInfo.setType(record.getInt("type"));
			userInfo.setCreateTime(record.get("create_time").toString());
			userInfo.setHeadImage(record.getStr("head_image"));
			setSessionAttr(PermissionChecker.USER_ID, record.get("id"));
			setSessionAttr(PermissionChecker.USER_USERNAME, username);
			setSessionAttr(PermissionChecker.USER,userInfo);
			String type ="2";
			if(!StrKit.isBlank(type)){
			    Record permission = Db.use("ta").findFirst("select * from ta_user_permission where user_type =? ",type);
				//将用户权限存入session
				setSessionAttr(PermissionChecker.USER_TYPE,permission.get("name"));
			}
			LOG.info("登录成功,username=" + username);
			 result = ResultFactory.buildSuccessResult(userInfo);
		} catch (Exception e) {
			LOG.error("出错啦", e);
			setAttr("error", e.getMessage());
			 result = ResultFactory.buildFailResult(e.getMessage());
			renderVelocity("register.html");
		}
		renderJson(result);
	}


}
