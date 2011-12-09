/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.security.openid;

import it.haslearnt.session.SessionKeys;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OpenIDAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private String openIdRegistrationUrl;
    private NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder;

    public OpenIDAuthenticationFailureHandler(String openIdRegistrationUrl, NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder) {
        this.openIdRegistrationUrl = openIdRegistrationUrl;
        this.normalizedOpenIdAttributesBuilder = normalizedOpenIdAttributesBuilder;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (openIdAuthenticationSuccesfullButUserIsNotRegistered(exception)) {
            redirectToOpenIdRegistrationUrl(request, response, exception);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    private void redirectToOpenIdRegistrationUrl(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        addOpenIdAttributesToSession(request, getOpenIdAuthenticationToken(exception));
        redirectStrategy.sendRedirect(request, response, openIdRegistrationUrl);
    }

    private void addOpenIdAttributesToSession(HttpServletRequest request, OpenIDAuthenticationToken openIdAuthenticationToken) throws ServletException {
        HttpSession session = request.getSession(false);
        sessionShouldBePresent(session);
        NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder.build(openIdAuthenticationToken);
        session.setAttribute(SessionKeys.openIdAttributes, normalizedOpenIdAttributes);
    }

    private void sessionShouldBePresent(HttpSession session) throws ServletException {
        if (session == null) {
            throw new ServletException("No session found");
        }
    }

    private boolean openIdAuthenticationSuccesfullButUserIsNotRegistered(AuthenticationException exception) {
        return exception instanceof UsernameNotFoundException &&
                exception.getAuthentication() instanceof OpenIDAuthenticationToken &&
                OpenIDAuthenticationStatus.SUCCESS.equals((getOpenIdAuthenticationToken(exception)).getStatus());
    }

    private OpenIDAuthenticationToken getOpenIdAuthenticationToken(AuthenticationException exception) {
        return ((OpenIDAuthenticationToken) exception.getAuthentication());
    }

}