package com.litetrade.gfc;

public class List_Data_sl {
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String idtype;
    private String idno;
    private String img;
    private String ifsc;
    private String bank_name;
    private String account_no;
    private String acc_hld_name;
    private String acc_ph_no;


    public List_Data_sl(String name, String email, String phone, String dob,String gender,String idtype,String idno,String img, String ifsc, String bank_name, String account_no, String acc_hld_name, String acc_ph_no) {
        this.name = name;
        this.email=email;
        this.phone=phone;
        this.dob=dob;
        this.gender=gender;
        this.idtype=idtype;
        this.idno=idno;
        this.img=img;
        this.ifsc=ifsc;
        this.bank_name=bank_name;
        this.account_no=account_no;
        this.acc_hld_name=acc_hld_name;
        this.acc_ph_no=acc_ph_no;

    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public String getDob() {
        return dob;
    }
    public String getGender() {
        return gender;
    }
    public String getIdtype() {
        return idtype;
    }
    public String getIdno() {
        return idno;
    }
    public String getImg() {
        return img;
    }
    public String getIfsc() {
        return ifsc;
    }
    public String getBank_name() {
        return bank_name;
    }
    public String getAccount_no() {
        return account_no;
    }
    public String getAcc_hld_name() {
        return acc_hld_name;
    }
    public String getAcc_ph_no() {
        return acc_ph_no;
    }


}