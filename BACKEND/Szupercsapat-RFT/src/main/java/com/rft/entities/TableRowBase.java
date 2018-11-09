package com.rft.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TableRowBase extends EntityBase {

	@Column(name = "from_year")
	private Integer fromYear;
	@Column(name = "to_year")
	private Integer toYear;
	private String name;

	public Integer getFromYear() {
		return fromYear;
	}

	public void setFromYear(Integer fromYear) {
		this.fromYear = fromYear;
	}

	public Integer getToYear() {
		return toYear;
	}

	public void setToYear(Integer toYear) {
		this.toYear = toYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
