package filters;

import org.eclipse.jetty.http.HttpMethod;
import utils.ParameterFromRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CalculatorFilterDivByZero implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        ParameterFromRequest pfr = new ParameterFromRequest(req);
        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            int b = pfr.getInt("b");
            String op = pfr.getStr("op");
            if ("div".equalsIgnoreCase(op) && b == 0) {
                response.getWriter().println("You can't divide by zero");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

}