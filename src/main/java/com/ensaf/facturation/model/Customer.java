package com.ensaf.facturation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor @AllArgsConstructor

@Data
@EqualsAndHashCode(callSuper = true, of = "id")
@ToString(callSuper = true)
public class Customer extends Entity {
	private String name;
	private String email;
	private String phone;
	private String address;

	/*
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}
	*/
}
