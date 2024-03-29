package com.ifeimo.im.common.bean.model;

import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.ifeimo.im.framwork.database.Fields;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;

/**
 * Created by lpds on 2017/4/19.
 * 用户模型
 */
public class AccountModel extends Model<AccountModel> {

    public static final String TB_NAME = Fields.AccounFields.TB_NAME;

    @TBPrimarykey
    private long id;

    @TBColumn(unique = true)
    private String memberId;

    @TBColumn
    private String member_nick_name;

    @TBColumn
    private String avatarUrl;

    @TBColumn
    private String update_time;

    public AccountModel() {

    }

    public AccountModel(String memberId, String member_nick_name, String avatarUrl) {
        this.memberId = memberId;
        this.member_nick_name = member_nick_name;
        this.avatarUrl = avatarUrl;
    }

    public AccountModel(Build build){
        setBuild(build);
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return member_nick_name;
    }

    public void setNickName(String member_nick_name) {
        this.member_nick_name = member_nick_name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String getTableName() {
        return TB_NAME;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getCreateTime() {
        return null;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", member_nick_name='" + member_nick_name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    public void setBuild(Build build){
        id = build.id;
        memberId = build.memberId;
        member_nick_name = build.member_nick_name;
        avatarUrl = build.avatarUrl;
        update_time = build.update_time;
    }

    public static class Build {

        public long id;

        public String memberId;

        public String member_nick_name;

        public String avatarUrl;

        public String update_time;


    }

}
