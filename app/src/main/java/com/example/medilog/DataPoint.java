package com.example.medilog;

public class DataPoint {
    int xValue;
    float yValue ;

    public DataPoint(int xValue , float yValue ){
        this.xValue = xValue;
        this.yValue = yValue ;
    }
    public DataPoint(){

    }
    public int getxValue(){
        return xValue;
    }

    public float getyValue(){
        return yValue;
    }



}
