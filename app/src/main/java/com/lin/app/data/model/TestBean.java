package com.lin.app.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by linhui on 2018/3/2.
 */
public class TestBean {

    String title;
    final List<String> text = new ArrayList<>();

    public TestBean() {
    }

    public TestBean(String title, Collection<String> text) {
        this.title = title;
        this.text.clear();
        this.text.addAll(text);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getText() {
        return text;
    }

    public void setText( Collection<String> text) {
        this.text.clear();
        this.text.addAll(text);
    }
}
