package com.example.bs00;

public class Item {
private String Waiter;
private  String Desc ;
private  int number;

    public Item(String waiter, String desc, int number) {
        Waiter = waiter;
        Desc = desc;
        this.number = number;
    }

    public Item(String waiter, String desc) {
        Waiter = waiter;
        Desc = desc;

    }



    public String getWaiter() {
        return Waiter;
    }

    public void setWaiter(String waiter) {
        this.Waiter = waiter;
    }


    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}