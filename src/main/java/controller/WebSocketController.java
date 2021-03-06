package controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import config.GetHttpSessionConfigurator;
import interceptor.PermissionChecker;
import model.Chat;
import model.Message;
import model.UserInfo;
import org.apache.log4j.Logger;

/**
 * @Author: toryxu
 * @Date: 2019/2/11 0011 13:16
 * @Version 1.0
 */
@ServerEndpoint(value="/websocket",configurator = GetHttpSessionConfigurator.class)
public class WebSocketController {
    public static final Logger LOG=Logger.getLogger(WebSocketController.class);
    public static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();
    public static Map<String,HttpSession> sessionMap=new HashMap<>();
    //与客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    public WebSocketController() {
    }
    @OnOpen
    public void onOpen(Session session,EndpointConfig  config) {
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if(httpSession.getAttribute(PermissionChecker.USER_ID)!=null)
        sessionMap.put(session.getId(), httpSession);
        this.session = session;
        webSocketSet.add(this);
//        onlineCount++;
    }

    @OnClose
    public void onClose(Session session) {
        sessionMap.remove(session.getId());
        webSocketSet.remove(this);
    }

    @OnMessage
    public void onMessage(String requestJson, Session session) throws IOException {
        //session1是发送者的httpsession
        HttpSession session1 = (HttpSession) sessionMap.get(session.getId());
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format1.format(date);
        int chatRoomId = (int) session1.getAttribute("chatRoomId");
        int id = (int) session1.getAttribute(PermissionChecker.USER_ID);
        UserInfo userInfo = new UserInfo();
        Record userRecord = Db.use("ta").findById("ta_user",id);
        userInfo.setNickname(userRecord.getStr("nickname"));
        userInfo.setUsername(userRecord.getStr("username"));
        userInfo.setType(userRecord.getInt("type"));
        userInfo.setCreateTime(userRecord.get("create_time").toString());
        userInfo.setHeadImage(userRecord.getStr("head_image"));
        userInfo.setUserid(userRecord.getInt("id"));
        for(WebSocketController item: webSocketSet){
            Message msg = new Message();
            msg.setDate(time);
            msg.setFrom(userInfo);
            msg.setText(requestJson);
            msg.setChatRoomId(chatRoomId);
            item.session.getBasicRemote().sendText(JSON.toJSONString(msg));
            LOG.info("websocket接收数据:"+JSON.toJSONString(msg));
        }
        String nickname = userInfo.getNickname();
        int userId = userInfo.getUserid();
        Record record = new Record();
        record.set("chatroom_id",chatRoomId);
        record.set("user_id",userId);
        record.set("nickname",nickname);
        record.set("content",requestJson);
        record.set("create_time",time);
        Db.use("ta").save("ta_chat",record);
        Db.use("ta").update("update ta_chatroom set chats_count = chats_count+1 where id="+chatRoomId);
    }
}
