package controller;

import annotation.PermissionOwn;
import com.jfinal.core.Controller;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

import java.util.ArrayList;
import java.util.List;
@PermissionOwn(name="student")
public class TestController extends Controller {
         
	public void index(){
		render("index3.jsp");
	}

	//@PermissionOwn("test")
}
