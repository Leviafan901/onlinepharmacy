package com.epam.pharmacy.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.epam.pharmacy.dao.Identified;

public class Medicine implements Serializable, Identified<Long> {

	private Long id;
	private String name;
	private Long countInStore;
	private Integer count;
	private Integer dosageMg;
	private boolean needPrescription;
	private BigDecimal price;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCountInStore() {
		return countInStore;
	}
	
	public void setCountInStore(Long countInStore) {
		this.countInStore = countInStore;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Integer getDosageMg() {
		return dosageMg;
	}
	
	public void setDosageMg(Integer dosageMg) {
		this.dosageMg = dosageMg;
	}

	public boolean isNeedPrescription() {
		return needPrescription;
	}
	
	public void setNeedPrescription(boolean needPrescription) {
		this.needPrescription = needPrescription;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((countInStore == null) ? 0 : countInStore.hashCode());
		result = prime * result + ((dosageMg == null) ? 0 : dosageMg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (needPrescription ? 1231 : 1237);
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicine other = (Medicine) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (countInStore == null) {
			if (other.countInStore != null)
				return false;
		} else if (!countInStore.equals(other.countInStore))
			return false;
		if (dosageMg == null) {
			if (other.dosageMg != null)
				return false;
		} else if (!dosageMg.equals(other.dosageMg))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (needPrescription != other.needPrescription)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}	
}
