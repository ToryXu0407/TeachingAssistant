package interceptor;

import annotation.UnCheckLogin;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.JsonRender;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionChecker implements Interceptor {

    private static final Logger LOG = Logger.getLogger("auth");

    public static final String USER_ID = "USER_ID";
    public static final String USER_USERNAME = "USER_USERNAME";
    public static final String USER_TYPE = "USER_TYPE";


    private static final String[] NOT_NEED_CHECK_URLS = new String[]{"/", "",
          //  "/perms/myaccount",


    };
    private static final String[] NOT_NEED_CHECK_FIRST_URLS = new String[]{
           // "/jfShopFind",
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void intercept(Invocation me) {

        String requestUrl = me.getActionKey();

        //获取IP地址
        String ip = me.getController().getRequest().getRemoteAddr();

        Class controllerClass = me.getController().getClass();

        //session验证
        HttpSession session = me.getController().getSession();

        UnCheckLogin methodOwn = getControllerMethodUnLoginOwn(controllerClass, me.getMethodName());
        if (methodOwn != null) {
            LOG.info("不需要登录,requestUrl=" + requestUrl);
            me.invoke();
            return;
        }

        if (session == null || session.getAttribute(USER_ID) == null) {
            LOG.info("未登录用户试图进入:" + requestUrl + ",IP:" + ip);
            me.getController().redirect("/login?redirect=" + requestUrl);
            return;
        }
        String username = (String) session.getAttribute(USER_USERNAME);
        //用户类型即Student/Teacher/Admin
        String userType = (String) session.getAttribute(USER_TYPE);
        if(requestUrl.contains(userType)){
            me.invoke();
            return;
        }
        //判断url是否属于可过滤的头url
        for (String url : NOT_NEED_CHECK_FIRST_URLS) {
            if (requestUrl.contains(url)) {
                me.invoke();
                return;
            }
        }
        System.out.println("用户权限不够,不能进入:" + requestUrl + ",IP:" + ip);
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", "对不起,您没有权限做此项操作!");
        map.put("result", "对不起,您没有权限做此项操作!");
        me.getController().render(new JsonRender(map).forIE());
    }

    //--------------以下是内部方法-----------

    private UnCheckLogin getControllerMethodUnLoginOwn(@SuppressWarnings("rawtypes") Class controllerClass, String methodName) {
        for (Method method : controllerClass.getMethods()) {
            if (methodName.equals(method.getName())) {
                return getUnLogin(method);
            }
        }
        return null;
    }

    private UnCheckLogin getUnLogin(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annt : annotations) {
            if (annt instanceof UnCheckLogin) {
                UnCheckLogin own = (UnCheckLogin) annt;
                return own;
            }
        }
        return null;
    }


}
