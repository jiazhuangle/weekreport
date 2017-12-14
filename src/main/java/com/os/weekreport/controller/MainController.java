package com.os.weekreport.controller;

import com.os.weekreport.dao.WorkReport;
import com.os.weekreport.dao.WorkReportDao;
import com.os.weekreport.service.WorkReportService;
import com.os.weekreport.view.WorkReportXlsxView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//import com.mysql.jdbc.StringUtils;

@Controller
public class MainController {

    @Autowired
    private WorkReportDao workReportDao;

    @Autowired
    WorkReportService workReportService;

    @GetMapping("/")
    public String index(Model model) {
        ArrayList<WorkReport> lastWeekReport = workReportService.getLastWeekReport();
        List<WorkReport> orderList = workReportService.getOrderList();
        model.addAttribute("lastweek", lastWeekReport);
        model.addAttribute("order",orderList);
        return "index";
    }

    @PostMapping("/save")
    public @ResponseBody
    String save(@RequestBody WorkReport workReport) {

        if (StringUtils.isEmpty(workReport.getId())) {
            String id = workReportDao.insert(workReport);
            return id;
        } else
            return workReportDao.update(workReport);
    }

    @GetMapping("/findThisWeek")
    public @ResponseBody
    List<WorkReport> findThisWeek() {
        ArrayList<WorkReport> thisWeekReport = workReportService.getThisWeekReport();
        return thisWeekReport;

    }

    @GetMapping("/get")
    public @ResponseBody
    WorkReport getWorkReportById(String id) {
        return workReportDao.getById(id);
    }

    @PostMapping("/delete")
    public @ResponseBody
    String delById(String id) {
        int re = workReportDao.delete(id);
        return String.valueOf(re);
    }

    @GetMapping("/leaders")
    public @ResponseBody
    ArrayList<String> getLeaders(String key) {
        return workReportService.getLeaders(key);
    }

    @GetMapping("/mission")
    public @ResponseBody
    ArrayList<String> getMissions(String key) {
        return workReportService.getMissions(key);
    }

    @PostMapping("/query")
    public @ResponseBody
    List<WorkReport> queryReport(@RequestBody WorkReport workReport) {

        return workReportService.queryWeekReport(workReport);
    }

    @GetMapping("/firstblood")
    public @ResponseBody
    WorkReport getFirstBlood() {
        WorkReport workReport = new WorkReport();
        workReport.setLeader(workReportService.getFirstBlood());
        return workReport;
    }

    @GetMapping("/export")
    public ModelAndView getExcel() {
        List<WorkReport> workReportList = workReportService.getThisWeekReport();
        //for test
        // List<WorkReport> workReportList = workReportService.getLastWeekReport();

        return new ModelAndView(new WorkReportXlsxView(), "workreports", workReportList);


    }
}

