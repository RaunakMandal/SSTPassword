package com.iamriju2000.sstpassword.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "finance")
public class Finance {
    @SerializedName("_id")
    @PrimaryKey
    @NonNull
    private String _id;
    @SerializedName("name")
    private String bankname;
    @SerializedName("user")
    private String user;
    @SerializedName("pass")
    private String pass;
    @SerializedName("web")
    private String web;

    @SerializedName("branch")
    private String branch;
    @SerializedName("acno")
    private String acno;
    @SerializedName("accname")
    private String accname;
    @SerializedName("ifsc")
    private String ifsc;
    @SerializedName("micr")
    private String micr;
    @SerializedName("profpass")
    private String profpass;
    @SerializedName("txnpass")
    private String txnpass;

    public Finance(String _id, String bankname, String user, String pass, String web, String branch, String acno, String accname, String ifsc, String micr, String profpass, String txnpass) {
        this._id = _id;
        this.bankname = bankname;
        this.user = user;
        this.pass = pass;
        this.web = web;
        this.branch = branch;
        this.acno = acno;
        this.accname = accname;
        this.ifsc = ifsc;
        this.micr = micr;
        this.profpass = profpass;
        this.txnpass = txnpass;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getProfpass() {
        return profpass;
    }

    public void setProfpass(String profpass) {
        this.profpass = profpass;
    }

    public String getTxnpass() {
        return txnpass;
    }

    public void setTxnpass(String txnpass) {
        this.txnpass = txnpass;
    }
}
