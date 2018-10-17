package controller;

import annotation.PermissionOwn;
import com.jfinal.core.Controller;
import interceptor.PermissionChecker;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

import java.util.ArrayList;
import java.util.List;
@PermissionOwn(name="student")
public class TestController extends Controller {
         
	public void index(){
		Long userid = getSessionAttr(PermissionChecker.USER_ID);
		String username = getSessionAttr(PermissionChecker.USER_USERNAME);
		setAttr("userid", userid);
		setAttr("username", username);
		render("liaotianshi.jsp");
	}

	//@PermissionOwn("test")
}
