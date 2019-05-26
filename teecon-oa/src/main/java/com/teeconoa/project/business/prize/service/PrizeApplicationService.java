package com.teeconoa.project.business.prize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.teeconoa.common.business.PrizeSenderFactory;
import com.teeconoa.project.business.prize.domain.SenderPrizeRequest;
import com.teeconoa.project.business.prize.domain.SenderPrizeRequest.Builder;

/**
*  Created by AndyYau
*  May 26, 2019 - 6:53:20 PM
*  Company: Teecon
**/
@Service
public class PrizeApplicationService {

	@Autowired
	private PrizeSenderFactory factory;
	
	@Autowired
	private ApplicationContext context;
	
	public void mockClient() {
		SenderPrizeRequest request = newPrizeSendRequestBuilder().prizeId(1).userId("A001").build();
		PrizeSender sender = factory.getPrizeSender(request);
		sender.sendPrize(request);
	}
	
	public Builder newPrizeSendRequestBuilder() {
		return context.getBean(Builder.class);
	}
}
