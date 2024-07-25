package com.blog.security;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blog.payload.JwtTokenHelper;

import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;




    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		1. get token 

		String requestToken = request.getHeader("Authorization");
		Enumeration<String> headerNames = request.getHeaderNames();

		while(headerNames.hasMoreElements())
		{
			logger.debug("Header {}", headerNames.nextElement());
		}

		logger.info(requestToken);

		String username = null;

		String token = null;

//		if (StringrequestToken != null && requestToken.startsWith("Bearer")) {
		if(StringUtils.hasText(requestToken) && requestToken.startsWith("Bearer ")) {

			token = requestToken.substring(7);

			try {
				username = this.jwtTokenHelper.extractUsername(token);
			} catch (IllegalArgumentException e) {
				logger.info("Unable to get Jwt token");
			} catch (ExpiredJwtException e) {
				logger.info("Jwt token has expired");
			} catch (MalformedJwtException e) {
				logger.info("invalid jwt");

			}

		} else {
			logger.info("Jwt token does not begin with Bearer");
		}

		// once we get the token , now validate

//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		if(StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			} else {
				logger.info("Invalid jwt token");
			}

		} else {
			logger.info("username is null or context is not null");
		}

		
		filterChain.doFilter(request, response);
	}
   
}
