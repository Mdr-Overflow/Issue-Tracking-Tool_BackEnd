package Issue.Tracking.Tool.LoginSessionPoint.filter;

import Issue.Tracking.Tool.LoginSessionPoint.securityConfig.ApiKeyAuthenticationToken;
import Issue.Tracking.Tool.LoginSessionPoint.service.PrivService;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Slf4j
public class ApiKeyAuthenticationFilter implements Filter {

    static final private String AUTH_METHOD = "api-key";
    //private  final AuthenticationManager authenticationManager;
  //  @Autowired
    static private apiKeyPairService apiKeyPairService;
    //private final keyPairRepo;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {


        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        assert webApplicationContext != null;
        apiKeyPairService apiKeyPairService = webApplicationContext.getBean(apiKeyPairService.class);



        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String apiKey = getApiKey((HttpServletRequest) request);
            if(apiKey != null) {
                 //   log.info("aaaaaaaaaa");
                try {
                    if((apiKeyPairService.validate(apiKey))) {

                        ApiKeyAuthenticationToken apiToken = new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.createAuthorityList("ROLE_USER"));
                        SecurityContextHolder.getContext().setAuthentication(apiToken);
                    } else {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        httpResponse.setStatus(401);
                        httpResponse.getWriter().write("Invalid API Key");
                        return;
                    }
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }

        chain.doFilter(request, response);

    }

    private String getApiKey(HttpServletRequest httpRequest) {
        String apiKey = null;

        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null) {
            authHeader = authHeader.trim();
            if(authHeader.toLowerCase().startsWith(AUTH_METHOD + " ")) {
                apiKey = authHeader.substring(AUTH_METHOD.length()).trim();
            }
        }
              /// Header : "Authorization" : "api-key ----
        return apiKey;
    }


}