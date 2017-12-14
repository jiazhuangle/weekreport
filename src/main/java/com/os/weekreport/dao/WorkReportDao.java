package com.os.weekreport.dao;

import com.os.weekreport.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class WorkReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insert(WorkReport workReport){
        String sql = "insert into workreport (mission , leader , goals , missionfrom , execution ,highlight,problems,remark,missionbegin,missionend) " +
                "values(?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //   jdbcTemplate.update(sql, new Object[]{workReport.getMission(), workReport.getLeader(), workReport.getGoals(), workReport.getFrom(), workReport.getExecution(), workReport.getHighlight(), workReport.getProblems(), workReport.getRemark(), new Date(workReport.getBegin().getTime()), new Date(workReport.getEnd().getTime())},keyHolder);
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,workReport.getMission());
                ps.setString(2,workReport.getLeader());
                ps.setString(3,workReport.getGoals());
                ps.setString(4,workReport.getMissionFrom());
                ps.setString(5,workReport.getExecution());
                ps.setString(6,workReport.getHighlight());
                ps.setString(7,workReport.getProblems());
                ps.setString(8,workReport.getRemark());
                ps.setDate(9,new Date(workReport.getMissionBegin().getTime()));
                ps.setDate(10,new Date(workReport.getMissionEnd().getTime()));
                System.out.println(ps.toString());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().toString();
    }

    public int delete(String id){
        String sql  = "delete from workreport where id = ?";
        int update = jdbcTemplate.update(sql, new Object[]{id});
        return update;
    }

    public String update(WorkReport workReport){
        String sql = "update workreport set mission=?,leader=?,goals=?,missionfrom=?,execution=?,highlight=?,problems=?,remark=?,missionbegin=?,missionend=? where id=?";
        jdbcTemplate.update(sql,new Object[]{workReport.getMission(),workReport.getLeader(),workReport.getGoals(),workReport.getMissionFrom(),workReport.getExecution(),workReport.getHighlight()
                                ,workReport.getProblems(),workReport.getRemark(),new Date(workReport.getMissionBegin().getTime()),new Date(workReport.getMissionEnd().getTime()),workReport.getId()
        });
        return workReport.getId();
    }

    public WorkReport getById(String id){
        String sql="select * from workreport where id=?";
        return jdbcTemplate.query(sql,new Object[]{id},resultSet ->{
            WorkReport workReport= new WorkReport();
            if (resultSet.next()){
                workReport.setId(resultSet.getString(1));
                workReport.setMission(resultSet.getString(2));
                workReport.setLeader(resultSet.getString(3));
                workReport.setGoals(resultSet.getString(4));
                workReport.setMissionFrom(resultSet.getString(5));
                workReport.setExecution(resultSet.getString(6));
                workReport.setHighlight(resultSet.getString(7));
                workReport.setProblems(resultSet.getString(8));
                workReport.setRemark(resultSet.getString(9));
                workReport.setMissionBegin(resultSet.getDate(10));
                workReport.setMissionEnd(resultSet.getDate(11));
            }
            return workReport;
        });
    }
    public ArrayList<WorkReport> getLastWeekReports(String begin, String end){
        String sql ="select * from workreport where missionbegin>= ? and missionend <= ?";
        return jdbcTemplate.query(sql, new Object[]{begin, end}, resultSet -> {
            ArrayList<WorkReport> workReports = new ArrayList<>();
            while (resultSet.next()){
                WorkReport workReport = new WorkReport();
                workReport.setId(resultSet.getString(1));
                workReport.setMission(resultSet.getString(2));
                workReport.setLeader(resultSet.getString(3));
                workReport.setGoals(resultSet.getString(4));
                workReport.setMissionFrom(resultSet.getString(5));
                workReport.setExecution(resultSet.getString(6));
                workReport.setHighlight(resultSet.getString(7));
                workReport.setProblems(resultSet.getString(8));
                workReport.setRemark(resultSet.getString(9));
                workReport.setMissionBegin(resultSet.getDate(10));
                workReport.setMissionEnd(resultSet.getDate(11));
                workReports.add(workReport);
            }
            return workReports;
        });
    }

    public ArrayList<WorkReport> getThisWeekReports(String date){
        String sql ="select * from workreport where recordtime > ? ";
        return jdbcTemplate.query(sql, new Object[]{date}, resultSet -> {
            ArrayList<WorkReport> workReports = new ArrayList<>();
            while (resultSet.next()){
                WorkReport workReport = new WorkReport();
                workReport.setId(resultSet.getString(1));
                workReport.setMission(resultSet.getString(2));
                workReport.setLeader(resultSet.getString(3));
                workReport.setGoals(resultSet.getString(4));
                workReport.setMissionFrom(resultSet.getString(5));
                workReport.setExecution(resultSet.getString(6));
                workReport.setHighlight(resultSet.getString(7));
                workReport.setProblems(resultSet.getString(8));
                workReport.setRemark(resultSet.getString(9));
                workReport.setMissionBegin(resultSet.getDate(10));
                workReport.setMissionEnd(resultSet.getDate(11));
                workReports.add(workReport);
            }
            return workReports;
        });
//        return null;
    }

    public ArrayList<WorkReport> getOrderList(String date){
        String sql ="select DISTINCT leader from workreport where recordtime > ? order by recordtime";
        return jdbcTemplate.query(sql, new Object[]{date}, resultSet -> {
            ArrayList<WorkReport> workReports = new ArrayList<>();
            while (resultSet.next()){
                WorkReport workReport = new WorkReport();

                workReport.setLeader(resultSet.getString(1));

                workReports.add(workReport);
            }
            return workReports;
        });
    }


    public ArrayList<String> getLeaders(String key){
        String conclusion ="%"+key+"%";
        String sql="select distinct leader from workreport where leader like ?";
        return jdbcTemplate.query(sql, new Object[]{conclusion}, resultSet -> {
            ArrayList<String> leaders = new ArrayList<>();
            while (resultSet.next()){
                leaders.add(resultSet.getString(1));

            }
            return leaders;
        });
    }

    public ArrayList<String> getMissions(String key){
        String conclusion ="%"+key+"%";
        String sql="select distinct mission from workreport where mission like ?";
        return jdbcTemplate.query(sql, new Object[]{conclusion}, resultSet -> {
            ArrayList<String> leaders = new ArrayList<>();
            while (resultSet.next()){
                leaders.add(resultSet.getString(1));

            }
            return leaders;
        });
    }

    public ArrayList<WorkReport> queryReports(String queryString){
        String sql="select * from workreport where "+queryString;
        //System.out.println(sql);
        return jdbcTemplate.query(sql,resultSet -> {
            ArrayList<WorkReport> workReports = new ArrayList<>();
            while (resultSet.next()){
                WorkReport workReport = new WorkReport();
                workReport.setId(resultSet.getString(1));
                workReport.setMission(resultSet.getString(2));
                workReport.setLeader(resultSet.getString(3));
                workReport.setGoals(resultSet.getString(4));
                workReport.setMissionFrom(resultSet.getString(5));
                workReport.setExecution(resultSet.getString(6));
                workReport.setHighlight(resultSet.getString(7));
                workReport.setProblems(resultSet.getString(8));
                workReport.setRemark(resultSet.getString(9));
                workReport.setMissionBegin(resultSet.getDate(10));
                workReport.setMissionEnd(resultSet.getDate(11));
                workReports.add(workReport);
            }
            return workReports;
        });
    }

    public String getFirstBlood(){
        String today = new DateTimeUtil().getToday();
       // System.out.println(today);
        //today="2017-12-01";
        String sql = "select leader from workreport t where t.recordtime> '"+today+"'  order by recordtime";
        return jdbcTemplate.query(sql,resultSet -> {
            String name="";
            if (resultSet.next()){
                name = resultSet.getString(1);
            }
            return name;
        });
    }
}
