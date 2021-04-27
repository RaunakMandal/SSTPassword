package com.iamriju2000.sstpassword.data;

import com.google.gson.annotations.SerializedName;

public class Finance {
    @SerializedName("_id")
    private String id;
    @SerializedName("bankname")
    private String bankname;
    @SerializedName("user")
    private String username;
    @SerializedName("pass")
    private String password;
    @SerializedName("web")
    private String website;

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

    public Finance(String id, String bankname, String username, String password, String website, String branch, String acno, String accname, String ifsc, String micr, String profpass, String txnpass) {
        this.id = id;
        this.bankname = bankname;
        this.username = username;
        this.password = password;
        this.website = website;
        this.branch = branch;
        this.acno = acno;
        this.accname = accname;
        this.ifsc = ifsc;
        this.micr = micr;
        this.profpass = profpass;
        this.txnpass = txnpass;
    }

    public String getId() {
        return id;
    }

    public String getBankname() {
        return bankname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }

    public String getBranch() {
        return branch;
    }

    public String getAcno() {
        return acno;
    }

    public String getAccname() {
        return accname;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getMicr() {
        return micr;
    }

    public String getProfpass() {
        return profpass;
    }

    public String getTxnpass() {
        return txnpass;
    }
}
