//package com.arck.springboot.app.view.xml;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.view.xml.MarshallingView;
//
//import com.arck.springboot.app.models.entity.Cliente;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component("listar.xml")
//public class ClienteListXmlView extends MarshallingView{
//
//	@Autowired
//	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
//		super(marshaller);
//	}
//
//	@Override
//	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		model.remove("titulo");
//			
//		@SuppressWarnings("unchecked")
//		List<Cliente> clientes = (List<Cliente>) model.get("clientes");
//		model.remove("clientes");
//		
//		model.put("clientes", clientes);
//		
//		super.renderMergedOutputModel(model, request, response);
//	}
//
//	
//}
