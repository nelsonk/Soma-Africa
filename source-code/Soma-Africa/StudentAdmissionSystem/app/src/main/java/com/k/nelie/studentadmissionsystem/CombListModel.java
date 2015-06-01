package com.k.nelie.studentadmissionsystem;

/**
 * Created by Nelie K on 2/28/2015.
 */
public class CombListModel {

    private String comb,cut;
    public CombListModel(String comb, String cut){
        this.comb=comb;
        this.cut=cut;

    }

    public String getComb(){
        return comb;
    }

    public String getCut(){
        return cut;
    }


    public void setCut(String cut){
        this.cut=cut;
    }
    public void setComb(String comb){
        this.comb=comb;
    }

}
