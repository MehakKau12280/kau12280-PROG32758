package ca.sheridancollege.kau12280.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.kau12280.database.DatabaseAccess;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		ca.sheridancollege.kau12280.beans.ErrorMessage user=da.findUserAccount(username);
		if(user==null)
		{
			System.out.println("User not found="+username);
			throw new UsernameNotFoundException("User not found="+username);
		}
		List<String> roleNameList=da.getRolesById(user.getUserId());
		List<GrantedAuthority> grantList=new ArrayList<GrantedAuthority>();
		if(roleNameList != null)
		{
			for(String role:roleNameList)
			{
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		UserDetails userDetails=(UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(),user.getEncryptedPassword(),grantList);
		return userDetails;
	}
}
