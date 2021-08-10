package com.donte.aluraflix.event;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviarEmailEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -7329637467949931931L;
	     
    private String subject;
 	private String template;
 	private String attachment;
 	private Map<String, Object> model;
 	private List<String> recips;
 	
 	public EnviarEmailEvent(Object source, String subject, String template, Map<String, Object> model, List<String> recips) {
		super(source);
		this.subject = subject;
		this.template = template;
		this.model = model;		
		this.recips = recips;
	}
	
}
