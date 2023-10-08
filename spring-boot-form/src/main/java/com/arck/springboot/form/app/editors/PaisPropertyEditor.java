package com.arck.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arck.springboot.form.app.services.PaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private PaisService service;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(null);
		if(text!=null && text.length()>0)
			try {
				this.setValue(service.obtenerPorId(Integer.parseInt(text)));
			}catch (NumberFormatException e) {
				this.setValue(null);
			}
	}

}
