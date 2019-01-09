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
            int page=0,pagesize=10;
            if(getParaToInt("page")!=null)
                page = getParaToInt("page");
            if(getParaToInt("pagesize")!=null)
                pagesize = getParaToInt("pagesize");
            String sql = "select * from ta_article ";
            if(getPara("isSticky")!=null)
                sql+=" where is_sticky='"+getPara("isSticky")+"'";
            String orderSql=" order by create_time desc";
            if(getPara("order")!=null) {
                if (getPara("order").equals("hot"))
                    //暂时把热门的定义为回帖量最多的，之后改成最近某段时间最多的
                    orderSql = " order by comment_count desc";
            }
            sql +=orderSql+" limit ?,?";
            List<Article> articles = new ArrayList<>();
            List<Record> records = Db.use("ta")
                    .find(sql,page,pagesize);
            if (records.size() != 0) {
                for (Record record : records) {
                    Article article = new Article();
                    article.setArticleId(record.getInt("article_id"));
                    article.setUserId(record.getInt("user_id"));
                    article.setCreateTime(record.get("create_time").toString());
                    article.setIsSticky(record.getStr("is_sticky"));
                    article.setLabel(record.getStr("label"));
                    article.setContent(record.getStr("content"));
                    article.setBrief(record.getStr("brief"));
                    article.setViewCount(record.getInt("view_count"));
                    article.setCommentCount(record.getInt("comment_count"));
                    Record record1 = Db.use("ta").
                            findFirst("select * from ta_user where id="+record.getInt("user_id"));
                    article.setUsername(record1.getStr("username"));
                    article.setHeadImage(record1.getStr("head_image"));
                    articles.add(article);
                }
                Record record = Db.use("ta")
                        .findFirst("select count(*) as num from ta_article");
                Long totalPage;
                Long articleNum = record.getLong("num");
                if(articleNum%10==0)
                    totalPage = articleNum/10L;
                else
                    totalPage = articleNum/10L+1;
                result = ResultFactory.buildSuccessArticleResult(articles,totalPage);
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
