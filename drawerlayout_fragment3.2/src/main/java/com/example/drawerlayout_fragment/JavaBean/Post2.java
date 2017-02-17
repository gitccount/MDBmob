package com.example.drawerlayout_fragment.JavaBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/10/25.
 */

public class Post2 extends BmobObject {
    private String title;// 帖子标题

    private String content;// 帖子内容
    private String time;//帖子发表时间

    private JkUser author;// 帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户

    private BmobFile image;// 帖子图片

    private BmobRelation likes;// 多对多关系：用于存储喜欢该帖子的所有用户

    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    //    private Pics pics;//多对对关系，用于在其它表收藏图片
//
//    public Pics getPics() {
//        return pics;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    }
//
//    public void setPics(Pics pics) {
//        this.pics = pics;
//    }

    public Post2() {
    }

    public Post2(String title, String content, String time, JkUser author, List<String> urls,
                 BmobRelation likes) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.urls = urls;
        this.likes = likes;
        this.time = time;
//        System.out.println("下载Post数据保存成功__________");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JkUser getAuthor() {
        return author;
    }

    public void setAuthor(JkUser author) {
        this.author = author;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}