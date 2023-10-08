package com.arck.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.arck.springboot.app.models.entity.Factura;
import com.arck.springboot.app.models.entity.Item;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Locale locale = localeResolver.resolveLocale(request);
		
		MessageSourceAccessor mensajes =  getMessageSourceAccessor();
		Factura factura = (Factura) model.get("factura");
		
		PdfPCell cell = new PdfPCell(new Phrase(messageSource.getMessage("pdf.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 219,255));
		cell.setPadding(8);
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		tabla.addCell(cell);
		tabla.addCell(factura.getCliente().getNombre()+" "+factura.getCliente().getNombre());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("pdf.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230,203));
		cell.setPadding(8);
		tabla2.setSpacingAfter(20);
		tabla2.addCell(cell);
		tabla2.addCell(mensajes.getMessage("pdf.table.identificador")+": "+factura.getId());
		tabla2.addCell(mensajes.getMessage("pdf.table.descripcion")+": "+factura.getDescripcion());
		tabla2.addCell(mensajes.getMessage("pdf.table.fecha")+": "+factura.getCreateAt());
		
		document.add(tabla);
		document.add(tabla2);
		

		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float [] {3,1,1,1});
		tabla3.addCell(mensajes.getMessage("pdf.table.producto"));
		tabla3.addCell(mensajes.getMessage("pdf.table.precio"));
		tabla3.addCell(mensajes.getMessage("pdf.table.cantidad"));
		tabla3.addCell(mensajes.getMessage("pdf.table.total"));
		
		for(Item item: factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());
		}
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(factura.getTotal().toString());
		
		document.add(tabla3);
	}
}
