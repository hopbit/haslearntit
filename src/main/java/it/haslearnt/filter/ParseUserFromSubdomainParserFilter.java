package it.haslearnt.filter;

import it.haslearnt.session.RequestKeys;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ParseUserFromSubdomainParserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String serverName = request.getServerName();
        List<String> elements = Arrays.asList(serverName.replace("/.*", "").split("\\."));
        setUserAtributeIfExistsInDomain(request, elements);
        chain.doFilter(request, response);
    }

    private void setUserAtributeIfExistsInDomain(ServletRequest request, List<String> elements) {
        switch(elements.size()) {
            case 0: throw new IllegalStateException("Weird server path");
            case 1: break;
            case 2: if(elements.contains("localhost")) {
                        setAtributefromfirstElement(request, elements);
                    }
                    break;
            default:
                setAtributefromfirstElement(request, elements);
                break;
        }
    }

    private void setAtributefromfirstElement(ServletRequest request, List<String> elements) {
        request.setAttribute(RequestKeys.userName, elements.get(0));
    }

    @Override
    public void destroy() {
    }
}
