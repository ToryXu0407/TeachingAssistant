package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import model.*;
import util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/2 0002 13:01
 * @Version 1.0
 */
@PermissionOwn(name="index")
public class ArticleController extends BaseController{
    /**
     * 显示帖子
     */
    @UnCheckLogin
    public void getArticle() {
        Result result;
        try {
            int userid=0;
            if(!String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                userid = getSessionAttr(PermissionChecker.USER_ID);
            int page=0,pagesize=10;
            if(getParaToInt("page")!=null)
                page = getParaToInt("page")-1;
            if(getParaToInt("pagesize")!=null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_article where 1=1";
            if(getPara("input")!=null){
                sql+=" and label like '%"+getPara("input")+"%'";
            }
            String orderSql="";
            if(getPara("isSticky")!=null)
                sql+=" and is_sticky='"+getPara("isSticky")+"'";
            if(getPara("order")!=null) {
                if (getPara("order").equals("hot"))
                    //暂时把热门的定义为回帖量最多的，之后改成最近某段时间最多的
                    orderSql = " order by comment_count desc";
            }else{
                orderSql=" order by create_time desc";
            }
            sql +=orderSql+" limit ?,?";
            List<Article> articles = new ArrayList<>();
            List<Record> records = Db.use("ta")
                    .find(sql,page*pagesize,pagesize);
            //获得article、发布者信息和个人是否点赞信息
            if (records.size() != 0) {
                for (Record record : records) {
                    Article article = new Article();
                    article.setId(record.getInt("id"));
                    article.setUserId(record.getInt("user_id"));
                    article.setCreateTime(record.get("create_time").toString());
                    article.setIsSticky(record.getStr("is_sticky"));
                    article.setLabel(record.getStr("label"));
                    article.setContent(record.getStr("content"));
                    article.setBrief(record.getStr("brief"));
                    article.setViewCount(record.getInt("view_count"));
                    article.setCommentCount(record.getInt("comment_count"));
                    article.setVoteCount(record.getInt("vote_count"));
                    Record record1 = Db.use("ta").
                            findFirst("select * from ta_user where id="+record.getInt("user_id"));
                    if(userid!=0){
                        Record record2 = Db.use("ta").findFirst("select * from ta_vote where article_id=? and user_id=?",record.getInt("id"),userid);
                        if(record2!=null)
                            article.setIsVoted(true);
                        else
                            article.setIsVoted(false);
                    }
                    article.setUsername(record1.getStr("username"));
                    article.setHeadImage(record1.getStr("head_image"));
                    article.setNickname(record1.getStr("nickname"));
                    articles.add(article);
                }
                Record record = Db.use("ta")
                        .findFirst("select count(*) as num from ta_article");
                Long totalPage;
                Long articleNum = record.getLong("num");
                if(articleNum%pagesize==0)
                    totalPage = articleNum/pagesize;
                else
                    totalPage = articleNum/pagesize+1;
                result = ResultFactory.buildSuccessArticleResult(articles,totalPage);
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
     * 显示帖子内容
     */
    @UnCheckLogin
    public void getArticleById() {
        Result result;
        try {
            if(getParaToInt("id")==0){
                throw new RuntimeException("未传入articleId!");
            }
            int userid=0;
            if(!String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                userid = getSessionAttr(PermissionChecker.USER_ID);
            String sql = "select * from ta_article where id="+getParaToInt("id");
            Record record = Db.use("ta")
                    .findFirst(sql);
            if(record!=null){
                    Db.use("ta").update("update ta_article set view_count = view_count+1 where id="+getParaToInt("id"));
                    Article article = new Article();
                    article.setId(record.getInt("id"));
                    article.setUserId(record.getInt("user_id"));
                    article.setCreateTime(record.get("create_time").toString());
                    article.setIsSticky(record.getStr("is_sticky"));
                    article.setLabel(record.getStr("label"));
                    article.setContent(record.getStr("content"));
                    article.setBrief(record.getStr("brief"));
                    article.setViewCount(record.getInt("view_count"));
                    article.setCommentCount(record.getInt("comment_count"));
                    article.setVoteCount(record.getInt("vote_count"));
                    Record record1 = Db.use("ta").
                            findFirst("select * from ta_user where id="+record.getInt("user_id"));
                    if(userid!=0){
                        Record record2 = Db.use("ta").findFirst("select * from ta_vote where article_id=? and user_id=?",record.getInt("id"),userid);
                        if(record2!=null)
                            article.setIsVoted(true);
                        else
                            article.setIsVoted(false);
                    }
                    article.setUsername(record1.getStr("username"));
                    article.setHeadImage(record1.getStr("head_image"));
                    article.setNickname(record1.getStr("nickname"));
                result = ResultFactory.buildSuccessResult(article);
            }else
                result = ResultFactory.buildFailResult("找不到article");
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
    /**
     * 新增或更新帖子
     */
    @UnCheckLogin
    public void updateArticle() {
        Result result;
        Article article = getBean(Article.class,"");
        int briefSize;
        try{
            if(article.getId()==0){
                Record record = new Record();
                if(String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
                    throw new RuntimeException("您还没有登陆!");
                record.set("user_id",getSessionAttr(PermissionChecker.USER_ID));
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                record.set("create_time",dateString);
                //默认文章没有加精
                record.set("is_sticky","N");
                record.set("label",article.getLabel());
                record.set("content",article.getContent());
                //简介设置为内容的前20位字符
                briefSize = (article.getContent().length()<20)?article.getContent().length():20;
                record.set("brief",article.getContent().substring(0,briefSize));
                Db.use("ta").save("ta_article",record);
            }else{
                Record record = new Record();
                record.set("id",article.getId());
                record.set("user_id",article.getUserId());
                record.set("update_time",new Date());
                record.set("is_sticky",article.getIsSticky());
                record.set("label",article.getLabel());
                record.set("content",article.getContent());
                briefSize = (article.getContent().length()<20)?article.getContent().length():20;
                record.set("brief",article.getContent().substring(0,briefSize));
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
     * 删帖
     */
    @UnCheckLogin
    public void delArticle() {
        Result result;
        int id = getParaToInt("id");
        try{
            Db.deleteById("ta_article",id);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }

    /**
     * 给主贴点赞
     */
    @UnCheckLogin
    public void vote(){
        Result result;
        int articleId = getParaToInt("articleId");
        if(String.valueOf(getSessionAttr(PermissionChecker.USER_ID)).equals("null"))
            throw new RuntimeException("您还没有登陆!");
        int userId = getSessionAttr(PermissionChecker.USER_ID);
        try{
            Record record = Db.use("ta").findFirst("select * from ta_vote where article_id=? and user_id = ?",articleId,userId);
            if(record!=null){
                Db.use("ta").delete("ta_vote",record);
                Db.use("ta").update("update ta_article set vote_count = vote_count-1 where id="+articleId);
            }else{
                Record record1 = new Record();
                record1.set("article_id",articleId);
                record1.set("user_id",userId);
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                record1.set("create_time",dateString);
                Db.use("ta").save("ta_vote",record1);
                Db.use("ta").update("update ta_article set vote_count = vote_count+1 where id="+articleId);
            }
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }
}
