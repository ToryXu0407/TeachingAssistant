package model;


import java.util.List;

/**
 * @Author: toryxu
 * @Date: 2019/4/3 0003 11:43
 * @Version 1.0
 */
public class ArticleRedis {

    List<Article> articleList;

    Long totalPage;

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
