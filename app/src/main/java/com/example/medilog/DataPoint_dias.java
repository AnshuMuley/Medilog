package com.example.medilog;

public class DataPoint_dias {
    int xValue;
    float yValue ;

    public DataPoint_dias(int xValue , float yValue ){
        this.xValue = xValue;
        this.yValue = yValue ;
    }
    public DataPoint_dias(){

    }
    public int getxValue(){
        return xValue;
    }

    public float getyValue(){
        return yValue;
    }
}
