package com.dl.activity.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Reward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "prize")
	private String prize;

	@Column(name = "quota")
	private BigDecimal quota;

	@Column(name = "people_num")
	private Integer peopleNum;

	@Column(name = "average")
	private BigDecimal average;

}
