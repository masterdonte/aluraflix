package com.donte.aluraflix.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -5515654299513607857L;
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException() {
		super("Erro na regra de negocio");
	}

}
