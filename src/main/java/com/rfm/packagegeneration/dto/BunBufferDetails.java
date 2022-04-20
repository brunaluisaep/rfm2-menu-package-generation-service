package com.rfm.packagegeneration.dto;

import org.springframework.stereotype.Service;

@Service
public class BunBufferDetails {
	//		<Bun id="22" name="Sourdogh" displayPriority="21" quantityToBuffer="0" bufferTime="120" status="ACTIVE"/>
		private Long bunId;
		private String bunName;
		private String displayPriority;
		private String quantityToBuffer;
		private String bufferTime;
		private String status;
		
		public Long getBunId() {
			return bunId;
		}
		public void setBunId(Long bunId) {
			this.bunId = bunId;
		}
		public String getBunName() {
			return bunName;
		}
		public void setBunName(String bunName) {
			this.bunName = bunName;
		}
		public String getDisplayPriority() {
			return displayPriority;
		}
		public void setDisplayPriority(String displayPriority) {
			this.displayPriority = displayPriority;
		}
		public String getQuantityToBuffer() {
			return quantityToBuffer;
		}
		public void setQuantityToBuffer(String quantityToBuffer) {
			this.quantityToBuffer = quantityToBuffer;
		}
		public String getBufferTime() {
			return bufferTime;
		}
		public void setBufferTime(String bufferTime) {
			this.bufferTime = bufferTime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
}