package com.teeconoa.project.business.prize.domain;

import com.teeconoa.common.business.PrizeTypeEnum;
import com.teeconoa.framework.web.domain.BaseEntity;

/**
*  Created by AndyYau
*  May 26, 2019 - 7:16:54 PM
*  Company: Teecon
**/
public class Prize extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3517194604681563249L;

	private int prizeId;
	
	private PrizeTypeEnum prizeType;
	
	private int amount;

	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public PrizeTypeEnum getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(PrizeTypeEnum prizeType) {
		this.prizeType = prizeType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
