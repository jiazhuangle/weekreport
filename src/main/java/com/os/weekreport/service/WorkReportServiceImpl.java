package com.os.weekreport.service;


import com.os.weekreport.dao.WorkReport;
import com.os.weekreport.dao.WorkReportDao;
import com.os.weekreport.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkReportServiceImpl implements WorkReportService{

    @Autowired
    WorkReportDao workReportDao;

    @Override
    public ArrayList<WorkReport> getLastWeekReport() {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        String lastMonday=dateTimeUtil.lastMonday();
        String lastFriday=dateTimeUtil.lastFirday();
        return workReportDao.getLastWeekReports(lastMonday, lastFriday);

    }

    @Override
    public ArrayList<WorkReport> getThisWeekReport() {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        return workReportDao.getThisWeekReports(dateTimeUtil.thisMonday());
//        return null;

    }


    @Override
    public ArrayList<String> getLeaders(String name) {
        return workReportDao.getLeaders(name);
    }

    @Override
    public ArrayList<String> getMissions(String name) {
        return workReportDao.getMissions(name);
    }

    @Override
    public ArrayList<WorkReport> queryWeekReport(WorkReport workReport) {
        String queryString ="";
        DateTimeUtil dateTimeUtil=new DateTimeUtil();
        if (!StringUtils.isEmpty(workReport.getLeader())){
            queryString += "leader like '%"+workReport.getLeader()+"%' ";
        }
        if (!StringUtils.isEmpty(workReport.getMissionBegin())){
            if (!queryString.equals("")){
                queryString +=" and ";
            }
            queryString +="missionbegin >= '"+dateTimeUtil.dateToString(workReport.getMissionBegin())+"' ";

        }
        if (!StringUtils.isEmpty(workReport.getMissionEnd())){

            if (!queryString.equals("")){
                queryString +=" and ";
            }
            queryString +="missionend <= '"+dateTimeUtil.dateToString(workReport.getMissionEnd())+"' ";

        }

        if (queryString.equals(""))
        {
            return null;
        }
        return workReportDao.queryReports(queryString);
    }

    @Override
    public String getFirstBlood() {
        return workReportDao.getFirstBlood();

    }

    @Override
    public List<WorkReport> getOrderList() {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        ArrayList<WorkReport> orderList = workReportDao.getOrderList(dateTimeUtil.thisMonday());
      //  ArrayList<WorkReport> orderList = workReportDao.getOrderList(dateTimeUtil.lastMonday());
        if (orderList.size()<3){
            return orderList;
        }
        else
            return orderList.subList(0,5);
    }
}