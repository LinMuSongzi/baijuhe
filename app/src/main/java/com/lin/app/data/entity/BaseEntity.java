package com.lin.app.data.entity;


/**
 * Created by lpds on 2017/6/13.
 */
public class BaseEntity<T extends BaseEntity> {

    protected String pic_url;

    protected int pic_rs;

    protected String title;

    protected String contentText;

    protected String time;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getPic_rs() {
        return pic_rs;
    }

    public T setPic_rs(int pic_rs) {
        this.pic_rs = pic_rs;
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "pic_url='" + pic_url + '\'' +
                ", pic_rs=" + pic_rs +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
