package com.stockercloud.aws.item.getshortages;

import java.util.List;

public class GetShortagesResponse {
	
	private List<Shortage> shortages;

	public List<Shortage> getShortages() {
		return shortages;
	}

	public void setShortages(List<Shortage> shortages) {
		this.shortages = shortages;
	}
}
