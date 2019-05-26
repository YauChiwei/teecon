package com.teeconoa.project.business.prize.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.teeconoa.common.business.PrizeTypeEnum;
import com.teeconoa.project.business.prize.mapper.PrizeService;

/**
*  Created by AndyYau
*  May 25, 2019 - 10:11:22 AM
*  Company: Teecon
**/
public class SenderPrizeRequest {
	
	private final PrizeTypeEnum prizeType;
	
	private final int amount;
	
	private final String userId;
	
	private String requestType;
	
	public SenderPrizeRequest(PrizeTypeEnum prizeType, int amount, String userId) {
		this.prizeType = prizeType;
		this.amount = amount;
		this.userId = userId;
	}
	
	@Component
	@Scope("prototype")
	public static class Builder{
		
		@Autowired
		PrizeService prizeService;
		
		private int prizeId;
		
		private String userId;
		
		public Builder prizeId(int prizeId) {
			this.prizeId = prizeId;
			return this;
		}
		
		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public SenderPrizeRequest build() {
			Prize prize = prizeService.findById(prizeId);
			return new SenderPrizeRequest(prize.getPrizeType(), prize.getAmount(), userId);
		}
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public PrizeTypeEnum getPrizeType() {
		return prizeType;
	}

	public int getAmount() {
		return amount;
	}

	public String getUserId() {
		return userId;
	}
	
}
