package com.arck.springboot.app.sec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

public class SimpleGrantedAuthorityExtra implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String role;

	public SimpleGrantedAuthorityExtra() {
		this.role = "";
	}

	public SimpleGrantedAuthorityExtra(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof SimpleGrantedAuthority sga) {
			return this.role.equals(sga.getAuthority());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.role.hashCode();
	}

	@Override
	public String toString() {
		return this.role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
}