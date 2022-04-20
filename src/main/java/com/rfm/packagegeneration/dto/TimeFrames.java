package com.rfm.packagegeneration.dto;

public class TimeFrames {
	
 private String from;	
 private String to;
 private Long prdInstId;
 
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public Long getPrdInstId() {
		return prdInstId;
	}

	public void setPrdInstId(Long prdInstId) {
		this.prdInstId = prdInstId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((prdInstId == null) ? 0 : prdInstId.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		TimeFrames other = (TimeFrames) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (prdInstId == null) {
			if (other.prdInstId != null)
				return false;
		} else if (!prdInstId.equals(other.prdInstId))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
