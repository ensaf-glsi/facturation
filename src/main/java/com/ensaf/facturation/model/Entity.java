package com.ensaf.facturation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor @AllArgsConstructor

@Data
public class Entity {
	private Long id;
}
