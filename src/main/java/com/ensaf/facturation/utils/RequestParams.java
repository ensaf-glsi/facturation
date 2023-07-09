package com.ensaf.facturation.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestParams {
	private final HttpServletRequest request;
	
	public String get(String name) {
		//TODO block injection javascript & html
		return request.getParameter(name);
	}
	
	public Long getLong(String name) {
		String value = get(name);
		if (StringUtils.isEmpty(value)) return null;
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			System.err.println("Error parsing long : " + value);
			return null;
		}
	}
	
	public <T> T getObject(Class<T> clazz) {
		//TODO creer un objet a partir des parametres en utilisant l'introspection
		return null;
	}

}
