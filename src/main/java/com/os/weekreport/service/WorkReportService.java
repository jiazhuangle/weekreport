package com.os.weekreport.service;

import com.os.weekreport.dao.WorkReport;

import java.util.ArrayList;
import java.util.List;

public interface WorkReportService {
    public ArrayList<WorkReport> getLastWeekReport();
    public ArrayList<WorkReport> getThisWeekReport();

    public ArrayList<String> getLeaders(String name);
    public ArrayList<String> getMissions(String name);
    public ArrayList<WorkReport> queryWeekReport(WorkReport workReport);

    public String getFirstBlood();
    public List<WorkReport> getOrderList();
}
