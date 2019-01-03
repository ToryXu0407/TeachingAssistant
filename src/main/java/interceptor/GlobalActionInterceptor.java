package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

/**
 * @Author: toryxu
 * @Date: 2018/12/24 0024 22:16
 * @Version 1.0
 */
public class GlobalActionInterceptor implements Interceptor {
    static Logger logger = Logger.getLogger("GlobalActionInterceptor");// 一般为当前的类名

    public void intercept(Invocation inv) {

        Controller controller = inv.getController();
        controller.getResponse().addHeader("Access-Control-Allow-Origin", "http://localhost:8000");
        inv.invoke();
    }
}

