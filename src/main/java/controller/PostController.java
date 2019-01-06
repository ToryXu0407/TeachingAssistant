package controller;

import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.Post;
import model.Result;
import model.ResultFactory;
import util.StringUtil;

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
        int articleId = getParaToInt("articleId");
        try{
            List<Record> recordList = Db.use("ta")
                    .find("select * from ta_post where article_id="+articleId);
            List<Post> postList = new ArrayList<>();
            for(Record record:recordList){
                Post post = new Post();
                post.setArticleId(record.getInt("article_id"));
                post.setUserId(record.getInt("user_id"));
                post.setPostId(record.getInt("post_id"));
                post.setParentUserId(record.getInt("parent_post_id"));
                post.setCreateTime(record.get("create_time").toString());
                post.setUpdateTime(record.get("update_time").toString());
                post.setContent(record.getStr("content"));
                postList.add(post);
            }
            result = ResultFactory.buildSuccessResult(postList);
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
            if(StringUtil.isEmpty(String.valueOf(post.getPostId()))){
                Record record = new Record();
                record.set("article_id",post.getArticleId());
                record.set("user_id",post.getUserId());
                record.set("parent_post_id",post.getParentUserId());
                record.set("create_time",new Date());
                record.set("content",post.getContent());
                Db.use("ta").save("ta_post",record);
            }else{
                Record record = new Record();
                record.set("post_Id",post.getPostId());
                record.set("article_id",post.getArticleId());
                record.set("user_id",post.getUserId());
                record.set("parent_post_id",post.getParentUserId());
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
        int postId = getParaToInt("postId");
        try{
            Db.deleteById("ta_post",postId);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
}
