package com.nttdata.bootcamp.productdomain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerPersonal {
	@NotNull
	private String id;
	private String dni;
	private String name;
	private String lastname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datebirth;
	private String phone;
	private String email;
	private String address;
}
