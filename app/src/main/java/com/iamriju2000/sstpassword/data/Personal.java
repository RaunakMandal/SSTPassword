package com.iamriju2000.sstpassword.data;

import com.google.gson.annotations.SerializedName;

public class Personal {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("user")
    private String username;
    @SerializedName("web")
    private String website;
    @SerializedName("pass")
    private String password;

    public Personal(String id, String name, String username, String website, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.website = website;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getWebsite() {
        return website;
    }

    public String getPassword() {
        return password;
    }
}
