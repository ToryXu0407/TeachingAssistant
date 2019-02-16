package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.Notice;
import model.Result;
import model.ResultFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/20 0020 23:24
 * @Version 1.0
 * 通知
 */
public class NoticeController extends BaseController {
    public static final Logger LOG=Logger.getLogger(NoticeController.class);
    /**
     * 查找对应courseId的所有通知，以时间排序。
     */
    @UnCheckLogin
    public void getNoticesByCourseId(){
        Result result;
        try {
            int page = 0, pagesize = 10;
            if(getParaToInt("id")==null){
                throw new RuntimeException("courseId为空！");
            }
            if(getParaToBoolean("created"))
            Db.use("ta").update("update ta_course set view_count = view_count+1 where id="+getParaToInt("id"));
            int id = getParaToInt("id");
            if (getParaToInt("page") != null)
                page = getParaToInt("page") - 1;
            if (getParaToInt("pagesize") != null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_notice where course_id =? order by create_time desc limit  ?,? ";
            List<Record> recordList = Db.use("ta").find(sql,id,page*pagesize,pagesize);
            List<Notice> noticeList = new ArrayList<>();
            for(Record record:recordList){
                Notice notice = new Notice();
                notice.setId(record.getInt("id"));
                notice.setContent(record.getStr("content"));
                notice.setCourseId(record.getInt("course_id"));
                notice.setName(record.getStr("name"));
                notice.setCreateTime(record.get("create_time").toString());
                if(record.get("update_time")!=null){
                    notice.setUpdateTime(record.get("update_time").toString());
                }
                notice.setImage(record.getStr("image"));
                noticeList.add(notice);
            }
            Record record = Db.use("ta")
                    .findFirst("select count(*) as num from ta_notice");
            Long totalPage;
            Long articleNum = record.getLong("num");
            if(articleNum%pagesize==0)
                totalPage = articleNum/pagesize;
            else
                totalPage = articleNum/pagesize+1;
            result = ResultFactory.buildSuccessArticleResult(noticeList,totalPage);
        }catch(Exception e){
        e.printStackTrace();
        LOG.error(e.getMessage(), e);
        result = ResultFactory.buildFailResult(e.getMessage());
    }
    renderJson(result);
    }

    /**
     * 删除通知
     */
    public void delNotice(){
        int id = getParaToInt("id");
        Result result;
        try{
            Db.use("ta").deleteById("ta_notice",id);
            result = ResultFactory.buildSuccessResult(id);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 添加通知
     */
    public void addNotice(){
        Notice notice = getBean(Notice.class,"");
        Result result;
        Date date = new Date();
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format1.format(date);
        notice.setCreateTime(time);
        Record record = new Record();
        record.set("name",notice.getName());
        record.set("course_id",notice.getCourseId());
        record.set("content",notice.getContent());
        record.set("create_time",time);
        try{
            Db.use("ta").save("ta_notice",record);
            result = ResultFactory.buildSuccessResult(notice);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
}
