package controller;

import annotation.PermissionOwn;
import annotation.UnCheckLogin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import interceptor.PermissionChecker;
import model.Article;
import model.Result;
import model.ResultFactory;
import model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/1/2 0002 13:01
 * @Version 1.0
 */
@PermissionOwn(name="index")
public class ArticleController extends BaseController{
    @UnCheckLogin
    public void getArticle() {
        Result result;
        List<Record> records = Db.use("ta")
                .find("select * from ta_article order by create_time desc limit 0,10 ");
        if(records.size()!=0){
            List<Article> articles = new ArrayList<>();
            for(Record record:records){
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
        renderJson(result);
    }

    @UnCheckLogin
    public void addArticle() {
        Result result;
        Article article = getBean(Article.class,"");
        Record record = new Record();
        record.set("article_id",article.getArticleId());
        record.set("user_id",article.getUserId());
        record.set("create_time",article.getCreateTime());
        record.set("is_sticky",article.getIsSticky());
        record.set("label",article.getLabel());
        record.set("content",article.getContent());
        record.set("brief",article.getBrief());
        Boolean rs= Db.use("ta").save("ta_article",record);
        if(rs){
            result = ResultFactory.buildSuccessResult(article);
            }else{
            result = ResultFactory.buildSuccessResult(null);
        }
        renderJson(result);
    }
}
