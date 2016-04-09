package com.dc.itcs.event.excel;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.Event;



public class EventExcelView extends AbstractExcelView{

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Event> list = (List<Event>) model.get("info");
		HSSFSheet sheet = workbook.createSheet("反馈查询清单");
		HSSFRow titleRow=sheet.createRow(0); //第0行
		titleRow.createCell(0).setCellValue("编号");
		titleRow.createCell(1).setCellValue("客户");
		titleRow.createCell(2).setCellValue("客户账号");
		titleRow.createCell(3).setCellValue("主题");
		titleRow.createCell(4).setCellValue("信息类型");
		titleRow.createCell(5).setCellValue("信息来源");
		titleRow.createCell(6).setCellValue("服务类型");
		titleRow.createCell(7).setCellValue("紧急程度");
		titleRow.createCell(8).setCellValue("提交人");
		titleRow.createCell(9).setCellValue("提交时间");
		titleRow.createCell(10).setCellValue("当前处理人");
		titleRow.createCell(11).setCellValue("状态");
		titleRow.createCell(12).setCellValue("解决时间");
		titleRow.createCell(13).setCellValue("最近更新");
		titleRow.createCell(14).setCellValue("超期预警");
		int rowNum = 1;
		for(Event mg : list){
			
			String overDu= ""; /**超期*/
			String warning= ""; /**预警*/
			if(mg.getIsOverduFlag() != null){
				if(mg.getIsOverduFlag() == ItcsConstants.IS_YES){
					overDu="超期";
				}
			}
			if(mg.getIsWarningFlag() != null){
				if(mg.getIsWarningFlag() == ItcsConstants.IS_YES){
					warning="预警";
				}
			}
			
			HSSFRow row=sheet.createRow(rowNum); //第1行	
			row.createCell(0).setCellValue(mg.getCode()==null?"":mg.getCode());
			row.createCell(1).setCellValue(mg.getTenant()==null?"":mg.getTenant().getName());
			row.createCell(2).setCellValue(mg.getSubmitUser()==null?"":mg.getSubmitUser().getUid());
			row.createCell(3).setCellValue(mg.getTopic()==null?"":mg.getTopic());
			row.createCell(4).setCellValue(mg.getMainType()==null?"":mg.getMainTypeText());
			row.createCell(5).setCellValue(mg.getSubmitType()==null?"":mg.getSubmitTypeText());
			row.createCell(6).setCellValue(mg.getServiceType()==null?"":mg.getServiceType().getName());
			row.createCell(7).setCellValue(mg.getServiceLevel()==null?"":mg.getServiceLevel().getName());
			row.createCell(8).setCellValue(mg.getSubmitUser()==null?"":mg.getSubmitUser().getUserName());
			row.createCell(9).setCellValue(mg.getSubmitTime()==null?"":mg.getSubmitTime());
			row.createCell(10).setCellValue(mg.getSingerIds()==null?"":mg.getSingerIdsText());
			row.createCell(11).setCellValue(mg.getMainState()==null?"":mg.getMainStateText());
			row.createCell(12).setCellValue(mg.getSolveTime()==null?"":mg.getSolveTime());
			row.createCell(13).setCellValue((mg.getRecentUser()==null?"":mg.getRecentUser())+(mg.getRecentAction()==null?"":mg.getRecentAction()));
			row.createCell(14).setCellValue(overDu+warning);
			
			rowNum++;
		}

		//response.setContentType("APPLICATION/vnd.ms-excel");  
		response.setContentType("application/msexcel");  
        response.setHeader("Content-Disposition", "attachment; filename="  
                + URLEncoder.encode("客户反馈.xls", "UTF-8")); 
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();
	}

}
