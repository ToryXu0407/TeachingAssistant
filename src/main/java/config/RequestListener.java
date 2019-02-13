package config;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: toryxu
 * @Date: 2019/2/11 0011 14:35
 * @Version 1.0
 */
public class RequestListener implements ServletRequestListener {
    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}
