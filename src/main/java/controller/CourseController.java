package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import interceptor.PermissionChecker;
import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import util.qiniuyun;

/**
 * @Author: toryxu
 * @Date: 2019/1/18 0018 13:26
 * @Version 1.0
 */
public class CourseController extends BaseController {
    public static final Logger LOG=Logger.getLogger(CourseController.class);
    /**
     * 在主页上显示部分帖子
     */
    @UnCheckLogin
    public void getCourse() {
        Result result;
        try {
            int page=0,pagesize=10;
            if(getParaToInt("page")!=null)
                page = getParaToInt("page")-1;
            if(getParaToInt("pagesize")!=null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_course where 1=1 ";
            if(getPara("input")!=null){
                sql += " and name like '%"+getPara("input")+"%'";
            }
            //根据点击量排序
            String orderSql=" order by view_count desc";
            sql +=orderSql+" limit ?,?";
            List<Course> courses = new ArrayList<>();
            List<Record> records = Db.use("ta")
                    .find(sql,page*pagesize,pagesize);
            if (records.size() != 0) {
                for (Record record : records) {
                    Course course = new Course();
                    course.setId(record.getInt("id"));
                    course.setName(record.getStr("name"));
                    course.setDescription(record.getStr("description"));
                    course.setCreateTime(record.get("create_time").toString());
                    course.setViewCount(record.getInt("view_count"));
                    course.setImage(record.getStr("image"));
                    courses.add(course);
                }
                Record record = Db.use("ta")
                        .findFirst("select count(*) as num from ta_course");
                Long totalPage;
                Long articleNum = record.getLong("num");
                if(articleNum%10==0)
                    totalPage = articleNum/pagesize;
                else
                    totalPage = articleNum/pagesize+1;
                result = ResultFactory.buildSuccessArticleResult(courses,totalPage);
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
     * 显示所查找的课程信息
     */
    @UnCheckLogin
    public void getCourseById() {
        Result result;
        try {
            if(getParaToInt("id")==0){
                throw new RuntimeException("未传入courseId!");
            }
            String sql = "select * from ta_course where id="+getParaToInt("id");
            Record record = Db.use("ta")
                    .findFirst(sql);
            if(record!=null){
                Course course = new Course();
                course.setId(record.getInt("id"));
                course.setName(record.getStr("name"));
                course.setDescription(record.getStr("description"));
                course.setCreateTime(record.get("create_time").toString());
                course.setViewCount(record.getInt("view_count"));
                course.setImage(record.getStr("image"));
                result = ResultFactory.buildSuccessResult(course);
            }else
                result = ResultFactory.buildFailResult("找不到course");
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 新增或更新课程信息（管理员或教师）
     */
    public void updateCourse() {
        Result result;
        Course course = getBean(Course.class,"");
        try{
            if(course.getId()==0){
                Record record = new Record();
                if(String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                    throw new RuntimeException("您还没有登陆!");
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                record.set("create_time",dateString);
                //默认文章没有加精
                record.set("description",course.getDescription());
                record.set("view_count",0);
                if(course.getImage()!=null)
                record.set("image",course.getImage());
                Db.use("ta").save("ta_course",record);
            }else{
                Record record = new Record();
                record.set("id",course.getId());
                record.set("description",course.getDescription());
                record.set("view_count",course.getViewCount());
                record.set("image",course.getImage());
                Db.use("ta").update("ta_article","id",record);
            }
            result = ResultFactory.buildSuccessResult(null);
        } catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 删除课程
     */
    public void delCourse() {
        Result result;
        int id = getParaToInt("id");
        try{
            Db.deleteById("ta_course",id);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 返回浏览量前5的课程（用来在首页轮播图显示)
     */
    @UnCheckLogin
    public void getCourselArticle(){
        Result result;
        try{
            List<Course> courses = new ArrayList<>();
            List<Record> recordList = Db.use("ta").find("select * from ta_course order by view_count desc limit 0,5");
            for(Record record:recordList){
                Course course = new Course();
                course.setId(record.getInt("id"));
                course.setName(record.getStr("name"));
                course.setDescription(record.getStr("description"));
                course.setCreateTime(record.get("create_time").toString());
                course.setViewCount(record.getInt("view_count"));
                course.setImage(record.getStr("image"));
                courses.add(course);
            }
            result = ResultFactory.buildSuccessResult(courses);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 添加课程
     * 将图片上传到七牛云，并返回图片路径
     */
    public void addCourse(){
        Result result;
        try{
            UploadFile file = getFile("file");
            String fileName = file.getFileName();
            String path = file.getUploadPath();
            String courseName = getPara("courseName");
            String description = getPara("description");
            String imageUrl = qiniuyun.upload(path+'/'+fileName,fileName);
            if(imageUrl==null){
                throw new Exception("上传失败！");
            }
            LOG.debug("addCourse上传 图片默认本地URL："+path);
            Date date = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format1.format(date);
            Record record = new Record();
            record.set("name",courseName);
            record.set("description",description);
            record.set("create_time",time);
            record.set("view_count",0);
            record.set("image",imageUrl);
            Db.use("ta").save("ta_course",record);
            result = ResultFactory.buildSuccessResult(imageUrl);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

}
