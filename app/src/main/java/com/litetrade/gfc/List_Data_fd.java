package com.litetrade.gfc;

public class List_Data_fd {
    private String RedChallenge;
    private String GreenChallenge;
    private String WinAmount;
    private String TotalAmount;






    public List_Data_fd(String RedChallenge, String GreenChallenge, String WinAmount, String TotalAmount) {
        this.RedChallenge = RedChallenge;
        this.GreenChallenge = GreenChallenge;
        this.WinAmount=WinAmount;
        this.TotalAmount=TotalAmount;


    }

    public String getRedChallenge() {
        return RedChallenge;
    }
    public String getGreenChallenge() {
        return GreenChallenge;
    }
    public String getWinAmount() {
        return WinAmount;
    }
    public String getTotalAmount() {
        return TotalAmount;
    }





}