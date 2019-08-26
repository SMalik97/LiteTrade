package com.litetrade.gfc;

public class List_Data_fd2 {
    private String Deposite;
    private String extraEarn;
    private String WinAmount;
    private String TotalAmount;






    public List_Data_fd2(String Deposite, String extraEarn, String WinAmount, String TotalAmount) {
        this.Deposite = Deposite;
        this.extraEarn = extraEarn;
        this.WinAmount=WinAmount;
        this.TotalAmount=TotalAmount;


    }

    public String getDeposite() {
        return Deposite;
    }
    public String getExtraEarn() {
        return extraEarn;
    }
    public String getWinAmount() {
        return WinAmount;
    }
    public String getTotalAmount() {
        return TotalAmount;
    }





}