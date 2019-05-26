package com.teeconoa.project.business.prize.mapper;

import com.teeconoa.project.business.prize.domain.Prize;

/**
*  Created by AndyYau
*  May 26, 2019 - 7:07:04 PM
*  Company: Teecon
**/
public interface PrizeService {

	Prize findById(int prizeId);
}
