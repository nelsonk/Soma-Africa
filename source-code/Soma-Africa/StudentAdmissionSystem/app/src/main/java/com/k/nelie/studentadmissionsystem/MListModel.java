package com.k.nelie.studentadmissionsystem;

/**
 * Created by Nelie K on 2/19/2015.
 */
public class MListModel {

    private String school,rank, status;
    public MListModel(String school, String rank, String status){
        this.school=school;
        this.rank=rank;
        this.status=status;

    }

    public String getSchool(){
        return school;
    }

    public String getRank(){
        return rank;
    }

    public String getStatus(){
        return status;
    }


    public void setSchool(String school){
        this.school=school;
    }
    public void setRank(String rank){
        this.rank=rank;
    }

    public void setStatus(String status){
        this.status=status;
    }

}
