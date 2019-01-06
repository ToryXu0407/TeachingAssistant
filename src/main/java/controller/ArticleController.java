package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.*;
import util.StringUtil;

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
     * 在主页上显示部分帖子，以时间排序
     */
    @UnCheckLogin
    public void getArticle() {
        Result result;
        try {
            List<Record> records = Db.use("ta")
                    .find("select * from ta_article order by create_time desc limit 0,10 ");
            if (records.size() != 0) {
                List<Article> articles = new ArrayList<>();
                for (Record record : records) {
                    Article article = new Article();
                    article.setArticleId(record.getInt("article_id"));
                    article.setUserId(record.getInt("user_id"));
                    article.setCreateTime(record.get("create_time").toString());
                    article.setIsSticky(record.getStr("is_sticky"));
                    article.setLabel(record.getStr("label"));
                    article.setContent(record.getStr("content"));
                    article.setBrief(record.getStr("brief"));
                    articles.add(article);
                }
                result = ResultFactory.buildSuccessResult(articles);
            }else
                result = ResultFactory.buildSuccessResult(null);
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
        try{
            if(StringUtil.isEmpty(String.valueOf(article.getArticleId()))){
                Record record = new Record();
                record.set("user_id",article.getUserId());
                record.set("create_time",new Date());
                record.set("is_sticky",article.getIsSticky());
                record.set("label",article.getLabel());
                record.set("content",article.getContent());
                record.set("brief",article.getBrief());
                Db.use("ta").save("ta_article",record);
            }else{
                Record record = new Record();
                record.set("article_id",article.getArticleId());
                record.set("user_id",article.getUserId());
                record.set("update_time",new Date());
                record.set("is_sticky",article.getIsSticky());
                record.set("label",article.getLabel());
                record.set("content",article.getContent());
                record.set("brief",article.getBrief());
                Db.use("ta").update("ta_article","article_id",record);
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
        int articleId = getParaToInt("articleId");
        try{
            Db.deleteById("ta_article",articleId);
            result = ResultFactory.buildSuccessResult(null);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            result = ResultFactory.buildFailResult(e.getMessage());
        }
        renderJson(result);
    }


}
