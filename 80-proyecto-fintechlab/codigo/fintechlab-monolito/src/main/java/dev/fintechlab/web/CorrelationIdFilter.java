package dev.fintechlab.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
    public static final String HEADER="X-Correlation-ID";
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException{
        String supplied=request.getHeader(HEADER); String id=supplied!=null&&supplied.matches("[A-Za-z0-9-]{1,64}")?supplied:UUID.randomUUID().toString();
        request.setAttribute("correlationId",id);response.setHeader(HEADER,id);MDC.put("correlationId",id);
        try{chain.doFilter(request,response);}finally{MDC.remove("correlationId");}
    }
}
