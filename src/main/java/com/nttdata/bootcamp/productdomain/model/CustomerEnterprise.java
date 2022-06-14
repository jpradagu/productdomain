package com.nttdata.bootcamp.productdomain.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerEnterprise {
	@NotNull
	private String id;
	private String ruc;
	private String reasonSocial;
	private String owner;
	private String address;

}
