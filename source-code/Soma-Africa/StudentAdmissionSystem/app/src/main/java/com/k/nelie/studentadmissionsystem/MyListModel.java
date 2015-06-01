package com.k.nelie.studentadmissionsystem;

/**
 * Created by Nelie K on 2/14/2015.
 */
public class MyListModel {

    private String prog,date;
    public MyListModel(String prog, String date){
        this.prog=prog;
        this.date=date;

    }

    public String getProg(){
        return prog;
    }

    public String getDate(){
        return date;
    }


    public void setDate(String date){
        this.date=date;
    }
    public void setProg(String prog){
        this.prog=prog;
    }
}
