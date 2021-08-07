package com.iamriju2000.sstpassword.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "personal")
public class Personal {
    @SerializedName("_id")
    @PrimaryKey
    @NonNull
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("user")
    private String user;
    @SerializedName("web")
    private String web;
    @SerializedName("pass")
    private String pass;

    public Personal(String _id, String name, String user, String web, String pass) {
        this._id = _id;
        this.name = name;
        this.user = user;
        this.web = web;
        this.pass = pass;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
