package com.teeconoa.common.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teeconoa.project.business.prize.domain.SenderPrizeRequest;
import com.teeconoa.project.business.prize.service.PrizeSender;

/**
*  Created by AndyYau
*  May 26, 2019 - 6:40:55 PM
*  Company: Teecon
**/
@Component
public class PrizeSenderFactory {

	@Autowired
	private List<PrizeSender> prizeSenderList;
	
	public PrizeSender getPrizeSender(SenderPrizeRequest request) {
		for(PrizeSender sender : prizeSenderList) {
			if(sender.support(request)) {
				return sender;
			}
		}
		
		throw new UnsupportedOperationException("Unsupported request "+ request.getRequestType());
	}
}
