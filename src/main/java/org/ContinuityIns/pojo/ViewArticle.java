package org.ContinuityIns.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//文章的预览类
@Getter
@Setter
public class ViewArticle extends PreviewArticle {
    //文章的评论列表
    private List<Comment> comments;

    public ViewArticle(Article article) {
        super(article);
    }
}
