package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import model.Post;
import model.Result;
import model.ResultFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/4 0004 17:34
 * @Version 1.0
 * 回帖
 */
public class PostController extends BaseController{

    /**
     * 根据articleId查看所属回帖
     */
    @UnCheckLogin
    public void getPostsByArticleId(){
        Result result;
        if(getParaToInt("articleId")==null||getParaToInt("articleId")==0){
            throw new RuntimeException("articleId为空!");
        }
        int articleId = getParaToInt("articleId");
        int page = 0;
        int userId=0;
        if(getParaToInt("page")!=null&&getParaToInt("page")!=0){
            page = getParaToInt("page")-1;
        }
        String sql = "select * from ta_post where parent_post_id=0 and article_id=? ";
        if(getParaToInt("only_owner")!=null&&getParaToInt("only_owner")!=0){
            Record record = Db.use("ta").findFirst("select * from ta_article where id="+articleId);
            userId = record.getInt("user_id");
            sql +="and user_id="+userId;
        }
        sql+= " limit ?,10";
        try{
            List<Record> recordList = Db.use("ta")
                    .find(sql,articleId,page*10);
            List<Post> postList = new ArrayList<>();
            for(Record record:recordList){
                Post post = new Post();
                post.setArticleId(record.getInt("article_id"));
                post.setUserId(record.getInt("user_id"));
                post.setId(record.getInt("id"));
                post.setParentPostId(record.getInt("parent_post_id"));
                post.setCreateTime(record.get("create_time").toString());
                post.setPosition(record.getInt("position"));
                if(record.get("update_time")!=null)
                post.setUpdateTime(record.get("update_time").toString());
                post.setContent(record.getStr("content"));
                Record record1 = Db.use("ta").findFirst("select * from ta_user where id="+record.getInt("user_id"));
                if(record1!=null){
                    post.setUsername(record1.getStr("username"));
                    post.setHeadImage(record1.getStr("head_image"));
                    post.setNickname(record1.getStr("nickname"));
                }
                List<Record> records = Db.use("ta").find("select * from ta_post where parent_post_id="+record.getInt("id"));
                List<Post> posts = new ArrayList<>();
                for(Record record3:records){
                    Post post2 = new Post();
                    post2.setArticleId(record3.getInt("article_id"));
                    post2.setUserId(record3.getInt("user_id"));
                    post2.setId(record3.getInt("id"));
                    post2.setParentPostId(record3.getInt("parent_post_id"));
                    post2.setCreateTime(record3.get("create_time").toString());
                    post2.setPosition(record3.getInt("position"));
                    post2.setToUserId(record3.getInt("to_user_id"));
                    record1 = Db.use("ta").findFirst("select * from ta_user where id="+record3.getInt("to_user_id"));
                    if(record1!=null)
                    post2.setToUserNickName(record1.getStr("nickname"));
                    if(record3.get("update_time")!=null)
                        post2.setUpdateTime(record3.get("update_time").toString());
                    post2.setContent(record3.getStr("content"));
                    record1 = Db.use("ta").findFirst("select * from ta_user where id="+record3.getInt("user_id"));
                    if(record1!=null){
                        post2.setUsername(record1.getStr("username"));
                        post2.setHeadImage(record1.getStr("head_image"));
                        post2.setNickname(record1.getStr("nickname"));
                    }
                    posts.add(post2);
                }
                post.setReplyNum(records.size());
                post.setReplies(posts);
                postList.add(post);
            }
            sql = "select count(*) as num from ta_post where parent_post_id =0 and article_id="+articleId;
            if(getParaToInt("only_owner")!=null&&getParaToInt("only_owner")!=0){
                sql += " and user_id="+userId;
            }
            Record record = Db.use("ta")
                    .findFirst(sql);
            Long totalPage;
            Long articleNum = record.getLong("num");
            if(articleNum%10==0)
                totalPage = articleNum/10L;
            else
                totalPage = articleNum/10L+1;
            result = ResultFactory.buildSuccessArticleResult(postList,totalPage);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 新增回帖
     */
    @UnCheckLogin
    public void updatePost() {
        Result result;
        Post post = getBean(Post.class,"");
        try{
            if(post.getId()==0){
                Record record = new Record();
                record.set("article_id",post.getArticleId());
                if(String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                    throw new RuntimeException("您还没有登陆!");
                //如果有父post，则说明是楼内回复，则默认设置其toUserId为自己的userid
                if(post.getParentPostId()!=0)
                    record.set("to_user_id",getSessionAttr(PermissionChecker.USER_ID));
                if(post.getToUserId()!=0)
                    record.set("to_user_id",post.getToUserId());
                record.set("user_id",getSessionAttr(PermissionChecker.USER_ID));
                record.set("parent_post_id",post.getParentPostId());
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                record.set("create_time",dateString);
                record.set("content",post.getContent());
                Record record1 = Db.use("ta").findFirst("select max(position) as num from ta_post where article_id="+post.getArticleId());
                if(record1.get("num")==null){
                    record.set("position",1);
                }else
                record.set("position",record1.getInt("num")+1);
                Db.use("ta").save("ta_post",record);
                Db.use("ta").update("update ta_article set comment_count = comment_count+1 where id = "+post.getArticleId());
            }else{
                Record record = new Record();
                record.set("id",post.getId());
                record.set("article_id",post.getArticleId());
                record.set("user_id",post.getUserId());
                record.set("parent_post_id",post.getParentPostId());
                record.set("update_time",new Date());
                record.set("content",post.getContent());
                Db.use("ta").save("ta_post",record);
            }
            result = ResultFactory.buildSuccessResult(null);
        } catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    @UnCheckLogin
    public void delPost() {
        Result result;
        int id = getParaToInt("id");
        try{
            Db.deleteById("ta_post",id);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    @UnCheckLogin
    public void getPostsByParentPostId(){
        if(getParaToInt("postId")==null||getParaToInt("postId")==0){
            throw new RuntimeException("postId为空!");
        }
        int parentPostId = getParaToInt("postId");
        int page = 0,pagesize=10;
        if(getParaToInt("page")!=null&&getParaToInt("page")!=0){
            page = getParaToInt("page")-1;
        }
        if(getParaToInt("pagesize")!=null&&getParaToInt("pagesize")!=0){
            pagesize = getParaToInt("pagesize");
        }
        List<Post> postList = new ArrayList<>();
        List<Record> recordList = Db.use("ta").find("select * from ta_post where parent_post_id = ? limit ?,?",parentPostId,page*pagesize,pagesize);
        for(Record record:recordList) {
            Post post = new Post();
            post.setArticleId(record.getInt("article_id"));
            post.setUserId(record.getInt("user_id"));
            post.setId(record.getInt("id"));
            post.setParentPostId(record.getInt("parent_post_id"));
            post.setCreateTime(record.get("create_time").toString());
            post.setPosition(record.getInt("position"));
            post.setToUserId(record.getInt("to_user_id"));
            if (record.get("update_time") != null)
                post.setUpdateTime(record.get("update_time").toString());
            post.setContent(record.getStr("content"));
            Record record1 = Db.use("ta").findFirst("select * from ta_user where id=" + record.getInt("user_id"));
            if (record1 != null) {
                post.setUsername(record1.getStr("username"));
                post.setHeadImage(record1.getStr("head_image"));
                post.setNickname(record1.getStr("nickname"));
            }
            postList.add(post);
        }
        recordList = Db.use("ta").find("select * from ta_post where parent_post_id = ?",parentPostId);
        int totalPage;
        int articleNum = recordList.size();
        if(articleNum%pagesize==0)
            totalPage = articleNum/pagesize;
        else
            totalPage = articleNum/pagesize+1;
        Result result = ResultFactory.buildSuccessArticleResult(postList,totalPage);
        renderJson(result);
    }
}
