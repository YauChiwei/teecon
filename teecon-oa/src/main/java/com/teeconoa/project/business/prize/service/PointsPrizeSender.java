package com.teeconoa.project.business.prize.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.teeconoa.common.business.PrizeTypeEnum;
import com.teeconoa.project.business.prize.domain.SenderPrizeRequest;

/**
*  Created by AndyYau
*  May 26, 2019 - 2:38:52 PM
*  Company: Teecon
**/
@Component
public class PointsPrizeSender implements PrizeSender {
	
	private static final Logger logger = LoggerFactory.getLogger(PointsPrizeSender.class);

	@Override
	public boolean support(SenderPrizeRequest request) {
		return request.getRequestType().equals(PrizeTypeEnum.POINT.name()) ? true : false;
	}

	@Override
	public void sendPrize(SenderPrizeRequest request) {
		logger.info("发放积分奖励");
	}

}
