package com.litetrade.gfc;

public class List_Data_cp {
    private String Phone;
    private String reffer;




    public List_Data_cp(String reffer, String Phone) {
        this.reffer=reffer;
        this.Phone = Phone;


    }
    public String getReffer() {
        return reffer;
    }
    public String getPhone() {
        return Phone;
    }




}