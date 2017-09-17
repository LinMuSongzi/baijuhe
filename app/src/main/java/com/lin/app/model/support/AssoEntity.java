package com.lin.app.model.support;

/**
 * Created by Hui on 2017/9/16.
 */

public class AssoEntity implements IInfoImpl {

    private String id;

    private String name;

    public AssoEntity() {
    }

    public AssoEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
