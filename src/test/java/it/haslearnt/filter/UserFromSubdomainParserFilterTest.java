package it.haslearnt.filter;

import it.haslearnt.session.RequestKeys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserFromSubdomainParserFilterTest {

    @Mock
    private ServletRequest request;
    @Mock
    private ServletResponse response;
    @Mock
    private FilterChain chain;

    String masterDomain = "haslearn.it/sth";
    String bob = "bob";

    @Test public void shouldNotAppendAnythingWhenMasterDomainGiven() throws IOException, ServletException {
        // given
        UserFromSubdomainParserFilter parser = prepareServerPath(masterDomain);

        // when
        parser.doFilter(request, response, chain);

        // then
        verify(request).getServerName();
        assertUsernameNotSet();
    }

    private void assertUsernameNotSet() {
        verify(request, times(0)).setAttribute(any(String.class), any());
    }

    @Test public void shouldAppendUsernameWhenSubDomainGiven() throws IOException, ServletException {
        // given
        UserFromSubdomainParserFilter parser = prepareServerPath(bob + "." + masterDomain);

        // when
        parser.doFilter(request, response, chain);

        // then
        verify(request).getServerName();
        verify(request).setAttribute(RequestKeys.userName, bob);
    }

    @Test
    public void shouldAppendUsernameWhenLocalhostSubdomain() throws IOException, ServletException {
        //given
        UserFromSubdomainParserFilter parser = prepareServerPath(bob + ".localhost");

        // when
        parser.doFilter(request, response, chain);

        // then
        verify(request).getServerName();
        verify(request).setAttribute(RequestKeys.userName, bob);
    }

    @Test
    public void shouldNotAppendUsernameWhenLocalhostWithoutSubdomain() throws IOException, ServletException {
        //given
        UserFromSubdomainParserFilter parser = prepareServerPath("localhost");

        // when
        parser.doFilter(request, response, chain);

        // then
        verify(request).getServerName();
        assertUsernameNotSet();
    }

    private UserFromSubdomainParserFilter prepareServerPath(String masterDomain) {
        UserFromSubdomainParserFilter parser = new UserFromSubdomainParserFilter();
        given(request.getServerName()).willReturn(masterDomain);
        return parser;
    }
}
