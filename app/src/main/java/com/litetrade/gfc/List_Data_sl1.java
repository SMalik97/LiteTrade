package com.litetrade.gfc;

public class List_Data_sl1 {
    private String email;
    private String wallet_balance;
    private String wining_blance;





    public List_Data_sl1(String email, String wallet_balance, String wining_blance) {
        this.email = email;
        this.wallet_balance = wallet_balance;
        this.wining_blance=wining_blance;


    }

    public String getEmail() {
        return email;
    }
    public String getWallet_balance() {
        return wallet_balance;
    }
    public String getWining_blance() {
        return wining_blance;
    }





}