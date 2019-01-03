package model;

import com.jfinal.plugin.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: toryxu
 * @Date: 2019/1/3 0003 14:29
 * @Version 1.0
 */
@Setter
@Getter
public class Article extends Model<Article> {

    private int articleId;

    private int userId;

    private String createTime;

    private String isSticky;

    private String label;

    private String content;

    private String brief;
}
