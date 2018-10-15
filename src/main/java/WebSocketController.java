//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@ServerEndpoint(value = "/websocket")
//public class WebSocketController {
//    public static Map<String, Session> conns = new HashMap<String, Session>();// session的map
//
//    /**
//     *
//     * @Title: OnOpen
//     * @Desc: 前端打开链接后，将执行此方法
//     *
//     * @param session
//     * @param config
//     *            参数
//     *
//     */
//    @OnOpen
//    public void OnOpen(Session session, EndpointConfig config) {
//        // System.out.println("---------open---------" + session.getId());
//        Session s = null;
//        for (String conn : conns.keySet()) {// 遍历已有的session（即已有的连接）
//            s = conns.get(conn);
//            synchronized (s) {
//                try {
//                    s.getBasicRemote().sendText(session.getId() + "进入了聊天室");// 根据session发送到对应页面
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        conns.put(session.getId(), session);
//    }
//
//    /**
//     *
//     * @Title: message
//     * @Desc: 消息处理，接收前端的send，并将消息返回给各个前端连接的页面
//     *
//     * @param message
//     * @param session
//     *            参数
//     *
//     */
//    @OnMessage
//    public void message(String message, Session session) {
//        // System.out.println("---------message---------" + session.getId());
//        Session s = null;
//        for (String conn : conns.keySet()) {
//            s = conns.get(conn);
//            synchronized (s) {
//                try {
//                    s.getBasicRemote()
//                            .sendText((session.getId() == s.getId() ? "我" : session.getId()) + " 说:" + message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     *
//     * @Title: close
//     * @Desc: 关闭连接，前端close方法时调用
//     *
//     * @param session
//     *            参数
//     *
//     */
//    @OnClose
//    public void close(Session session) {
//        //System.out.println("---------close---------" + session.getId());
//        Session s = null;
//        for (String conn : conns.keySet()) {
//            s = conns.get(conn);
//            if (s != session)
//                synchronized (s) {
//                    try {
//                        s.getBasicRemote().sendText(session.getId() + "离开了聊天室");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        }
//        conns.remove(session.getId());
//    }
//
//    /**
//     *
//     * @Title: close
//     * @Desc: 系统出错时调用
//     *
//     * @param session
//     *            参数
//     *
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("error");
//        error.printStackTrace();
//    }
//}
//
