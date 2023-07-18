package com.example.ppqppl_inpatient_ward_server.utils;

public class PageSelect {
    //当前页
    private int pageIndex = 0;
    //总记录数
    private int totalRecordSum = 0;
    //每页记录数
    private int pageSize = 10;
    //总页数
    private int totalPageSum;
    //当前页记录数
    private int recordNum;

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalRecordSum() {
        return totalRecordSum;
    }

    public void setTotalRecordSum(int totalRecordSum) {
        int totalPageSum = totalRecordSum/pageSize;
        if(totalRecordSum%pageSize != 0){
            totalPageSum += 1;
        }
        this.setTotalPageSum(totalPageSum);
        this.totalRecordSum = totalRecordSum;
    }

    public int getPageSize() {
        this.pageSize = 5;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageSum() {
        //获得总页数
        this.totalPageSum = (getPageSize() + getTotalRecordSum() - 1) / getPageSize();
        return totalPageSum;
    }

    public void setTotalPageSum(int totalPageSum) {
        this.totalPageSum = totalPageSum;
    }

    //得到起始页面数
    public int getStartNum(){
        return (getPageIndex() - 1) * getPageSize();
    }
}

