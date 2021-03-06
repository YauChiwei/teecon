package com.teeconoa.project.business.prize.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.teeconoa.common.business.PrizeTypeEnum;
import com.teeconoa.project.business.prize.domain.SenderPrizeRequest;

/**
*  Created by AndyYau
*  May 26, 2019 - 6:31:49 PM
*  Company: Teecon
**/
@Component
public class VirtualCurrencyPrizeSender implements PrizeSender {

	private static final Logger logger = LoggerFactory.getLogger(VirtualCurrencyPrizeSender.class);
	
	@Override
	public boolean support(SenderPrizeRequest request) {
		return request.getRequestType().equals(PrizeTypeEnum.VIRTUAL_CURRENCY.name()) ? true : false;
	}

	@Override
	public void sendPrize(SenderPrizeRequest request) {
		logger.info("发放虚拟货币奖励");

	}

}
