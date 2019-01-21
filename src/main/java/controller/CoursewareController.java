package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.Courseware;
import model.Notice;
import model.Result;
import model.ResultFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/20 0020 23:24
 * @Version 1.0
 * 课件
 */
public class CoursewareController extends BaseController {
    /**
     * 查找对应courseId的所有课件，以时间排序。
     */
    @UnCheckLogin
    public void getCoursewareByCourseId(){
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
            String sql = "select * from ta_courseware where course_id =? order by create_time desc limit  ?,? ";
            List<Record> recordList = Db.use("ta").find(sql,id,page*pagesize,pagesize);
            List<Courseware> coursewareList = new ArrayList<>();
            for(Record record:recordList){
                Courseware courseware = new Courseware();
                courseware.setId(record.getInt("id"));
                courseware.setCourseId(record.getInt("course_id"));
                courseware.setName(record.getStr("name"));
                courseware.setCreateTime(record.get("create_time").toString());
                if(record.get("update_time")!=null){
                    courseware.setUpdateTime(record.get("update_time").toString());
                }
                courseware.setUrl(record.getStr("url"));
                coursewareList.add(courseware);
            }
            Record record = Db.use("ta")
                    .findFirst("select count(*) as num from ta_courseware");
            Long totalPage;
            Long articleNum = record.getLong("num");
            if(articleNum%pagesize==0)
                totalPage = articleNum/pagesize;
            else
                totalPage = articleNum/pagesize+1;
            result = ResultFactory.buildSuccessArticleResult(coursewareList,totalPage);
        }catch(Exception e){
        e.printStackTrace();
        LOG.error(e.getMessage(), e);
        result = ResultFactory.buildFailResult(e.getMessage());
    }
    renderJson(result);
    }
}
