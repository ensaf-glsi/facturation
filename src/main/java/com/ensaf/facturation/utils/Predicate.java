package com.ensaf.facturation.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Predicate {

	private String condition;
	private Object value;
}
