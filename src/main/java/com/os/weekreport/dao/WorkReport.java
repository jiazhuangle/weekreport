package com.os.weekreport.dao;

import java.sql.Timestamp;
import java.util.Date;

public class WorkReport {
    private String id;
    private String mission;
    private String missionFrom;
    private String leader;
    private String goals;
    private String execution;
    private String highlight;
    private String problems;
    private String remark;
    private Date missionBegin;
    private Date missionEnd;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }


    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getMissionFrom() {
        return missionFrom;
    }

    public void setMissionFrom(String missionFrom) {
        this.missionFrom = missionFrom;
    }

    public Date getMissionBegin() {
        return missionBegin;
    }

    public void setMissionBegin(Date missionBegin) {
        this.missionBegin = missionBegin;
    }

    public Date getMissionEnd() {
        return missionEnd;
    }

    public void setMissionEnd(Date missionEnd) {
        this.missionEnd = missionEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
