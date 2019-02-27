package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import model.*;
import org.apache.log4j.Logger;
import util.qiniuyun;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/20 0020 23:24
 * @Version 1.0
 * 课件
 */
public class CoursewareController extends BaseController {
    public static final Logger LOG=Logger.getLogger(CoursewareController.class);
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
    /**
     * 删除通知
     */
    public void delCourseware(){
        int id = getParaToInt("id");
        Result result;
        try{
            Db.use("ta").deleteById("ta_courseware",id);
            result = ResultFactory.buildSuccessResult(id);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 添加课程课件
     * 将文件上传到七牛云，并返回文件路径
     */
    @UnCheckLogin
    public void addCourseWare(){
        Result result;
        try{
            UploadFile file = getFile("file");
            String fileName = file.getFileName();
            String path = file.getUploadPath();
            String name = fileName;
            String courseId = getPara("courseId");
            String imageUrl = qiniuyun.upload(path+'/'+fileName,fileName);
            if(imageUrl==null){
                throw new Exception("上传失败！");
            }
            LOG.info("addCourseWare上传 文件默认本地URL："+path);
            Date date = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format1.format(date);
            Record record = new Record();
            record.set("name",name);
            record.set("course_id",courseId);
            record.set("url",imageUrl);
            record.set("create_time",time);
            Db.use("ta").save("ta_courseware",record);
            result = ResultFactory.buildSuccessResult(imageUrl);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
}
