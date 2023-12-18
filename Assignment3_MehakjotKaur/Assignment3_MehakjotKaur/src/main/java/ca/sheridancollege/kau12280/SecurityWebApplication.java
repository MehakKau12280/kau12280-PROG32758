
package ca.sheridancollege.kau12280;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ca.sheridancollege.kau12280.security.SecurityConfig;

public class SecurityWebApplication extends AbstractSecurityWebApplicationInitializer 
{
	public SecurityWebApplication()
	{
		super(SecurityConfig.class);
	}
}





