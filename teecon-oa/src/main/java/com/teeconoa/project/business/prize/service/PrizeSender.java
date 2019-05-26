/**
 * 
 */
package com.teeconoa.project.business.prize.service;

import com.teeconoa.project.business.prize.domain.SenderPrizeRequest;

/**
*  Created by AndyYau
*  May 25, 2019 - 10:09:35 AM
*  Company: Teecon
*  
*  奖品发放接口
**/

public interface PrizeSender {

	boolean support(SenderPrizeRequest request);
	
	void sendPrize(SenderPrizeRequest request);
}
