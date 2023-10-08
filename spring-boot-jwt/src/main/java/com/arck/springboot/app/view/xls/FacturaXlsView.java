package com.arck.springboot.app.view.xls;

import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.arck.springboot.app.models.entity.Factura;
import com.arck.springboot.app.models.entity.Item;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver.xlsx")
public class FacturaXlsView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		
		MessageSourceAccessor mensajes =  getMessageSourceAccessor();
		Factura factura = (Factura) model.get("factura");
		
		Sheet sheet= workbook.createSheet("factura Spring");
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensajes.getMessage("pdf.datos.cliente"));
		

		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre()+" "+factura.getCliente().getApellido());
		

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("pdf.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("pdf.table.identificador")+": "+factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("pdf.table.descripcion")+": "+factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("pdf.table.fecha")+": "+factura.getCreateAt());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		
		sheet.createRow(9).createCell(0).setCellValue(mensajes.getMessage("pdf.table.producto"));
		sheet.getRow(9).createCell(1).setCellValue(mensajes.getMessage("pdf.table.precio"));
		sheet.getRow(9).createCell(2).setCellValue(mensajes.getMessage("pdf.table.cantidad"));
		sheet.getRow(9).createCell(3).setCellValue(mensajes.getMessage("pdf.table.total"));

		sheet.getRow(9).getCell(0).setCellStyle(theaderStyle);	
		sheet.getRow(9).getCell(1).setCellStyle(theaderStyle);	
		sheet.getRow(9).getCell(2).setCellStyle(theaderStyle);	
		sheet.getRow(9).getCell(3).setCellStyle(theaderStyle);		
		
		int rowNum = 10;
		for(Item item: factura.getItems()) {
			sheet.createRow(rowNum).createCell(0).setCellValue(item.getProducto().getNombre());
			sheet.getRow(rowNum).createCell(1).setCellValue(item.getProducto().getPrecio().toString());
			sheet.getRow(rowNum).createCell(2).setCellValue(item.getCantidad());
			sheet.getRow(rowNum).createCell(3).setCellValue(item.calcularImporte().toString());
			sheet.getRow(rowNum).getCell(0).setCellStyle(tbodyStyle);
			sheet.getRow(rowNum).getCell(1).setCellStyle(tbodyStyle);
			sheet.getRow(rowNum).getCell(2).setCellStyle(tbodyStyle);
			sheet.getRow(rowNum).getCell(3).setCellStyle(tbodyStyle);
			rowNum++;
		}
		rowNum++;
		
		sheet.createRow(rowNum).createCell(0).setCellValue(mensajes.getMessage("pdf.table.total")+":");
		sheet.getRow(rowNum).createCell(1).setCellValue(factura.getTotal().toString());
	}

}
