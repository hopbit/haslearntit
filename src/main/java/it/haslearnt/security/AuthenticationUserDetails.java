/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.security;

import it.haslearnt.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("serial")
public class AuthenticationUserDetails implements UserDetails {
	private final String userName;
	private final String passwordHash;
	private final boolean enabled;
	private HashSet<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

	public AuthenticationUserDetails(User user) {
		this.userName = user.name();
		this.passwordHash = user.password();
		this.enabled = true;
		this.grantedAuthorities.addAll(new HashSet<GrantedAuthority>());
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}