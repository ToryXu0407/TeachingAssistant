package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import model.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author: toryxu
 * @Date: 2019/2/9 0009 20:45
 * @Version 1.0
 */
@PermissionOwn(name="chat")
public class ChatController extends BaseController{
    public static final Logger LOG=Logger.getLogger(ChatController.class);
    /**
     * 获得进入聊天室的用户数量
     */
    @UnCheckLogin
    public void getEnteredUsers() {
        Result result;
        try{
            int chatRoomId = getParaToInt("chatRoomId");
            List<UserInfo> userInfos = new ArrayList<>();
            List<Integer> list = new ArrayList<>();
            Map<String,HttpSession> sessionMap = WebSocketController.sessionMap;
            for(String key:sessionMap.keySet()){
                int id = (int) sessionMap.get(key).getAttribute("chatRoomId");
                if(id==chatRoomId){
                    list.add((Integer) sessionMap.get(key).getAttribute(PermissionChecker.USER_ID));
                }
            }
            StringBuilder ret = new StringBuilder();
            sqlKit.joinIds(list,ret);
            if(ret.length()!=0){
                List<Record> recordList = Db.use("ta").find("select * from ta_user where id in"+ret);
                for(Record userRecord:recordList){
                    UserInfo userInfo = new UserInfo();
                    userInfo.setNickname(userRecord.getStr("nickname"));
                    userInfo.setUsername(userRecord.getStr("username"));
                    userInfo.setType(userRecord.getInt("type"));
                    userInfo.setCreateTime(userRecord.get("create_time").toString());
                    userInfo.setHeadImage(userRecord.getStr("head_image"));
                    userInfo.setUserid(userRecord.getInt("id"));
                    userInfos.add(userInfo);
                }
            }
            result = ResultFactory.buildSuccessResult(userInfos);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 通过Id获得聊天室信息
     */
    @UnCheckLogin
    public void getChatRoomById() {
        Result result;
        try{
            int id =getParaToInt("id");
            Record record = Db.use("ta").findFirst("select * from ta_chatroom where id = "+id);
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(record.getInt("id"));
            chatRoom.setTeacherId(record.getInt("teacher_id"));
            chatRoom.setStartTime(record.get("start_time").toString());
            chatRoom.setEndTime(record.get("end_time").toString());
            chatRoom.setCreateTime(record.get("create_time").toString());
            chatRoom.setCourseName(record.getStr("course_name"));
            chatRoom.setChatsCount(record.getInt("chats_count"));
            result = ResultFactory.buildSuccessResult(chatRoom);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 显示所有/或条件内聊天室
     */
    @UnCheckLogin
    public void getChatRooms() {
        Result result;
        try {
            int status = 0;
            int page=0,pagesize=10;
            if(getParaToInt("page")!=null)
                page = getParaToInt("page")-1;
            if(getParaToInt("pagesize")!=null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_chatroom where 1=1";
            //根据聊天室开启状态查询
            if(getParaToInt("status")!=null){
                status = getParaToInt("status");
                switch (status){
                    //未开始状态，开始时间大于当前时间
                    case 1:
                        sql+=" and start_time>now() ";
                        break;
                    //进行中状态，开始时间小于当前时间并且结束时间大于当前时间
                    case 0:
                        sql+=" and start_time<now() and end_time>now()";
                        break;
                    //已结束状态，结束时间小于当前时间
                    case 2:
                        sql+=" and end_time<now()";
                        break;
                        default:
                            break;
                }
            }
            //根据查询内容搜索
            if(getPara("input")!=null&&!getPara("input").isEmpty()){
                sql+=" and course_name like '%"+getPara("input")+"%'";
            }
            String orderSql="";
            //根据筛选条件热度/时间查询
            if(getPara("order")!=null) {
                if (getPara("order").equals("hot"))
                    orderSql = " order by chats_count desc";
            }else{
                orderSql = " order by create_time desc";
            }
            sql +=orderSql+" limit ?,?";
            List<ChatRoom> chatRooms = new ArrayList<>();
            List<Record> records = Db.use("ta")
                    .find(sql,page*pagesize,pagesize);
            if (records.size() != 0) {
                for (Record record : records) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setId(record.getInt("id"));
                    chatRoom.setTeacherId(record.getInt("teacher_id"));
                    chatRoom.setStartTime(record.get("start_time").toString());
                    chatRoom.setEndTime(record.get("end_time").toString());
                    chatRoom.setCreateTime(record.get("create_time").toString());
                    chatRoom.setCourseName(record.getStr("course_name"));
                    chatRoom.setChatsCount(record.getInt("chats_count"));
                    Record record1 = Db.use("ta").
                            findFirst("select * from ta_user where id="+record.getInt("teacher_id"));
                    chatRoom.setUsername(record1.getStr("username"));
                    chatRoom.setHeadImage(record1.getStr("head_image"));
                    chatRoom.setNickname(record1.getStr("nickname"));
                    chatRooms.add(chatRoom);
                }
                Record record = Db.use("ta")
                        .findFirst("select count(*) as num from ta_chatroom");
                Long totalPage;
                Long articleNum = record.getLong("num");
                if(articleNum%pagesize==0)
                    totalPage = articleNum/pagesize;
                else
                    totalPage = articleNum/pagesize+1;
                result = ResultFactory.buildSuccessArticleResult(chatRooms,totalPage);
            }else
                result = ResultFactory.buildFailResult("null");
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 将HttpSession的加入attribute-chatroomid
     */
    @UnCheckLogin
    public void setChatRoomId(){
        Result result;
        try{
            int chatRoomId = getParaToInt("chatRoomId");
            setSessionAttr("chatRoomId",chatRoomId);
            Record record = Db.use("ta").findFirst("select * from ta_chatroom where id = "+chatRoomId);
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(record.getInt("id"));
            chatRoom.setTeacherId(record.getInt("teacher_id"));
            chatRoom.setStartTime(record.get("start_time").toString());
            chatRoom.setEndTime(record.get("end_time").toString());
            chatRoom.setCreateTime(record.get("create_time").toString());
            chatRoom.setCourseName(record.getStr("course_name"));
            chatRoom.setChatsCount(record.getInt("chats_count"));
            result = ResultFactory.buildSuccessResult(chatRoom);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 创建聊天室
     */
    public void addChatRoom(){
        Result result;
        try{
            int userId =0;
            if(!String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                userId = getSessionAttr(PermissionChecker.USER_ID);
            else
                throw new Exception("未登陆!");
            String content = getPara("content");
            String startTime = getPara("startTime");
            String endTime = getPara("endTime");
            Record record = new Record();
            record.set("teacher_id",userId);
            record.set("course_name",content);
            record.set("start_time",startTime);
            record.set("end_time",endTime);
            Date date = new Date();
            java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time = format1.format(date);
            record.set("create_time",time);
            Db.use("ta").save("ta_chatroom",record);
            result = ResultFactory.buildSuccessResult(null);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 删除聊天室
     */
    public void delChatRoom() {
        Result result;
        int id = getParaToInt("id");
        try{
            Db.deleteById("ta_chatroom",id);
            Db.use("ta").update("delete from ta_chat where chatroom_id="+id);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 在聊天室关闭后
     * 获得当前聊天室所有聊天记录
     */
    @UnCheckLogin
    public void getChats(){
        Result result;
        try{
            int chatRoomId = getParaToInt("chatRoomId");
            int userId =0;
            if(!String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                userId = getSessionAttr(PermissionChecker.USER_ID);
            setSessionAttr("chatRoomId",chatRoomId);
            List<Record> records = Db.use("ta").find("select * from ta_chat where chatroom_id = "+chatRoomId);
            List<Message> messages = new ArrayList<>();
            for(Record record:records){
                Message message = new Message();
                message.setId(record.getInt("id"));
                message.setChatRoomId(record.getInt("chatroom_id"));
                message.setNickname(record.getStr("nickname"));
                message.setDate(record.get("create_time").toString());
                message.setMessage(record.getStr("content"));
                if(userId!=record.getInt("user_id")){
                    message.setIsSelf(0);
                }else{
                    message.setIsSelf(1);
                }
                messages.add(message);
            }
            result = ResultFactory.buildSuccessResult(messages);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

}
