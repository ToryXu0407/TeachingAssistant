package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.Notice;
import model.Result;
import model.ResultFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/20 0020 23:24
 * @Version 1.0
 * 通知
 */
public class NoticeController extends BaseController {
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
            int id = getParaToInt("id");
            if (getParaToInt("page") != null)
                page = getParaToInt("page") - 1;
            if (getParaToInt("pagesize") != null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_notice where course_id =? limit  ?,? order by create_time desc";
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
}
