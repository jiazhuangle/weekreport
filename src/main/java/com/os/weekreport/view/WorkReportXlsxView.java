package com.os.weekreport.view;


import com.os.weekreport.dao.WorkReport;
import com.os.weekreport.utils.DateTimeUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class WorkReportXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        String header = "attachment; filename=weekreport-" + dateTimeUtil.getToday() + ".xlsx";
        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition", header);

        Sheet excelSheet = workbook.createSheet("卓越工程部周报");
        excelSheet.autoSizeColumn(1);
        excelSheet.autoSizeColumn(6);
        excelSheet.autoSizeColumn(7);
        excelSheet.autoSizeColumn(8);
        excelSheet.autoSizeColumn(11);
        excelSheet.autoSizeColumn(13);
        excelSheet.setColumnWidth(1, 16 * 256);
        excelSheet.setColumnWidth(6, 40 * 256);
        excelSheet.setColumnWidth(7, 27 * 256);
        excelSheet.setColumnWidth(8, 40 * 256);
        excelSheet.setColumnWidth(11, 20 * 256);
        excelSheet.setColumnWidth(13, 20 * 256);

        //表头格式
        CellStyle headCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headCellStyle.setFont(font);
        headCellStyle.setWrapText(true);
        setExcelHeader(excelSheet, headCellStyle);

        CellStyle rowCellStyle = workbook.createCellStyle();
        rowCellStyle.setWrapText(true);


        List<WorkReport> workreports = (List<WorkReport>) map.get("workreports");
        setExcelRows(excelSheet, rowCellStyle, workreports);
    }

    private void setExcelHeader(Sheet excelSheet, CellStyle cellStyle) {

        Row header = excelSheet.createRow(0);
        Cell cell;
        cell = header.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(1);
        cell.setCellValue("工作项目/任务");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(2);
        cell.setCellValue("任务来源");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(3);
        cell.setCellValue("任务性质");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(4);
        cell.setCellValue("测试负责人");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(5);
        cell.setCellValue("研发负责人");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(6);
        cell.setCellValue("本周工作目标");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(7);
        cell.setCellValue("实际完成时间");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(8);
        cell.setCellValue("实际完成情况");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(9);
        cell.setCellValue("测试人员及工时");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(10);
        cell.setCellValue("亮点");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(11);
        cell.setCellValue("问题点");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(12);
        cell.setCellValue("修改内容简述");
        cell.setCellStyle(cellStyle);

        cell = header.createCell(13);
        cell.setCellValue("备注");
        cell.setCellStyle(cellStyle);

    }

    private void setExcelRows(Sheet excelSheet, CellStyle cellStyle, List<WorkReport> list) {
        int line = 1;
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        for (WorkReport workReport : list) {
            int no = line++;
            Row r = excelSheet.createRow(no);

            Cell cell;
            cell = r.createCell(0);
            cell.setCellValue(no);
            cell.setCellStyle(cellStyle);

            cell = r.createCell(1);
            cell.setCellValue(workReport.getMission());
            cell.setCellStyle(cellStyle);

            cell = r.createCell(5);
            cell.setCellValue(workReport.getLeader());
            cell.setCellStyle(cellStyle);

            cell = r.createCell(6);
            cell.setCellValue(workReport.getGoals());
            cell.setCellStyle(cellStyle);

            String date = dateTimeUtil.dateToString(workReport.getMissionBegin()) + "到" + dateTimeUtil.dateToString(workReport.getMissionEnd());
            cell = r.createCell(7);
            cell.setCellValue(date);
            cell.setCellStyle(cellStyle);

            cell = r.createCell(8);
            cell.setCellValue(workReport.getExecution());
            cell.setCellStyle(cellStyle);

            cell = r.createCell(10);
            cell.setCellValue(workReport.getHighlight());
            cell.setCellStyle(cellStyle);

            cell = r.createCell(11);
            cell.setCellValue(workReport.getProblems());
            cell.setCellStyle(cellStyle);

            cell = r.createCell(13);
            cell.setCellValue(workReport.getRemark());
            cell.setCellStyle(cellStyle);

        }
    }
}
