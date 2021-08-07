package com.donte.aluraflix.config.jwtauth;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.donte.aluraflix.config.AluraflixProperty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@Profile("jwt-auth")
public class JwtTokenService{
	
	@Autowired
	private AluraflixProperty props;
	
	private final static String ROLES = "ROLES";

/*	private final static String secretKey = "shgfsfgsfghksjfgsjdfgdfhgjgdfshdgf";

	private final long expiration = 1000 * 60 * 60 * 3;*/

	public Collection<GrantedAuthority> getAuthoritiesFromToken(String token) {
		Collection<GrantedAuthority> authorities = getRolesFromToken(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		return authorities;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String generateToken(Authentication auth) {
		List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		final Date now = new Date();
        Date validaty = new Date(now.getTime() + props.getJwt().getExpiration());
        
		return Jwts.builder().setSubject(auth.getName()).claim(ROLES, roles).setIssuedAt(now)
				.setExpiration(validaty).signWith(SignatureAlgorithm.HS512, props.getJwt().getSecret()).compact();		
	}

	public boolean validateToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		final Date expiration = claims.getExpiration();
		return expiration.after(new Date());		
	}
	
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRolesFromToken(String token) {		
		return getClaimFromToken(token, claims -> (List<String>) claims.get(ROLES));
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(props.getJwt().getSecret()).parseClaimsJws(token).getBody();
	}

	public Authentication getAuthentication(String token) {
		String username = getUsernameFromToken(token);
		Collection<GrantedAuthority> authorities = getAuthoritiesFromToken(token);
		UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(username, "", authorities);
		return userPassAuthToken;
	}	

}