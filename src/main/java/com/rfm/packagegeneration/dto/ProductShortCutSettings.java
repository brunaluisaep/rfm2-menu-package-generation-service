package com.rfm.packagegeneration.dto;

import java.util.List;

public class ProductShortCutSettings {

	private Long productId;
	private String name;
	private Long kioskId;
	private Long setId;
	private List<ShortCutDetails> item;
	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShortCutDetails> getItem() {
		return item;
	}

	public void setItem(List<ShortCutDetails> item) {
		this.item = item;
	}

	public Long getKioskId() {
		return kioskId;
	}

	public void setKioskId(Long kioskId) {
		this.kioskId = kioskId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Override
	public String toString() {
		return "ProductShortCutSettings [productId=" + productId + ", name=" + name + ", kioskId=" + kioskId + ", item="
				+ item + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((kioskId == null) ? 0 : kioskId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((setId == null) ? 0 : setId.hashCode());
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
		ProductShortCutSettings other = (ProductShortCutSettings) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (kioskId == null) {
			if (other.kioskId != null)
				return false;
		} else if (!kioskId.equals(other.kioskId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (setId == null) {
			if (other.setId != null)
				return false;
		} else if (!setId.equals(other.setId))
			return false;
		return true;
	}
}
