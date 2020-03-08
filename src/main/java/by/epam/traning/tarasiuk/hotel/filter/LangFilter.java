package by.epam.traning.tarasiuk.hotel.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@WebFilter(filterName = "filter3", urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "defaultLocale", value = "ru"),
        @WebInitParam(name = "supportedLocales", value = "en,ru")
})
public class LangFilter implements Filter {

    private String defaultLocale;
    private List<String> supportedLocales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        supportedLocales = Arrays.asList(filterConfig.getInitParameter("supportedLocales").split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String locale = (String) session.getAttribute("locale");
        if (isNull(locale)) {
            String browserLocale = servletRequest.getLocale().getLanguage();
            if (supportedLocales.contains(browserLocale)) {
                session.setAttribute("locale", browserLocale);
            } else {
                session.setAttribute("locale", defaultLocale);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        defaultLocale = null;
    }
}
