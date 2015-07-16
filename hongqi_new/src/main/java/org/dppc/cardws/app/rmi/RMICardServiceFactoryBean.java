package org.dppc.cardws.app.rmi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

//@Component("RMICardService")
public class RMICardServiceFactoryBean extends RmiProxyFactoryBean {
	@Value("${RMI.cardservice.url}")
	private String rmiCardUrl;
	
	private void initialize() {
		this.setServiceInterface(RMICardService.class);
		this.setServiceUrl(rmiCardUrl);
	}

	@Override
	public void afterPropertiesSet() {
		initialize();
		try {
			super.afterPropertiesSet();
		} catch (RemoteLookupFailureException e) {
			
		}
	}
}
