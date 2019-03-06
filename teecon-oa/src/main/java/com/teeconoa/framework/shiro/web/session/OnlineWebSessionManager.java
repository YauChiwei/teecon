package com.teeconoa.framework.shiro.web.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.project.monitor.online.service.UserOnlineServiceImpl;
import com.teeconoa.common.constant.ShiroConstants;
import com.teeconoa.common.utils.spring.SpringUtils;
import com.teeconoa.project.monitor.online.domain.OnlineSession;
import com.teeconoa.project.monitor.online.domain.UserOnline;

/**
*  Created by AndyYau
*  2019年3月4日 - 下午5:23:04
*  Company: Teecon
**/
public class OnlineWebSessionManager extends DefaultWebSessionManager{
	private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

	@Override
	public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
		// TODO Auto-generated method stub
		super.setAttribute(sessionKey, attributeKey, value);
		if(value != null && needMarkAttributeChanged(attributeKey)) {
			OnlineSession session = (OnlineSession)this.doGetSession(sessionKey);
			session.markAttributeChanged();
		}
		
	}
	
    private boolean needMarkAttributeChanged(Object attributeKey)
    {
        if (attributeKey == null)
        {
            return false;
        }
        String attributeKeyStr = attributeKey.toString();
        // 优化 flash属性没必要持久化
        if (attributeKeyStr.startsWith("org.springframework"))
        {
            return false;
        }
        if (attributeKeyStr.startsWith("javax.servlet"))
        {
            return false;
        }
        if (attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME))
        {
            return false;
        }
        return true;
    }

	@Override
	public void validateSessions() {
		if (log.isInfoEnabled())
        {
            log.info("invalidation sessions...");
        }

        int invalidCount = 0;

        int timeout = (int) this.getGlobalSessionTimeout();
        Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        UserOnlineServiceImpl userOnlineService = SpringUtils.getBean(UserOnlineServiceImpl.class);
        List<UserOnline> userOnlineList = userOnlineService.selectOnlineByExpired(expiredDate);
        // 批量过期删除
        List<String> needOfflineIdList = new ArrayList<String>();
	}

	@Override
	public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
        Object removed = super.removeAttribute(sessionKey, attributeKey);
        if (removed != null)
        {
            OnlineSession s = (OnlineSession) doGetSession(sessionKey);
            s.markAttributeChanged();
        }

        return removed;
	}

	
}
