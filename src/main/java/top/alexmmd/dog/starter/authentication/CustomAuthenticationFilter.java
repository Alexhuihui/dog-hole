package top.alexmmd.dog.starter.authentication;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import top.alexmmd.dog.enums.LoginTypeEnum;
import top.alexmmd.dog.starter.authentication.processor.DelegateLoginPostProcessor;
import top.alexmmd.dog.starter.authentication.processor.LoginPostProcessor;

/**
 * @author wangyonghui
 * @date 2022年12月15日 09:25:00
 */
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_LOGIN_TYPE_KEY = "loginType";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/auth", "POST");

    private static final LoginPostProcessor DEFAULT_LOGIN_POST_PROCESSOR = new DelegateLoginPostProcessor();

    public static final String POST = "POST";

    private boolean postOnly = true;

    private String loginTypeParameter = SPRING_SECURITY_LOGIN_TYPE_KEY;

    public CustomAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals(POST)) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 从request请求体中拿到body中的JSON对象参数
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String jsonBody = requestWrapper.getBody();
        log.info(jsonBody);
        JSONObject object = JSONUtil.parseObj(jsonBody);
        LoginTypeEnum loginTypeEnum = obtainLoginType(object);

        // 根据不同的登录类型，包装不同的Authentication
        AbstractAuthenticationToken authRequest = DEFAULT_LOGIN_POST_PROCESSOR
                .wrapperRequest(object, loginTypeEnum);
        if (ObjectUtil.isNull(authRequest)) {
            throw new AuthenticationServiceException("待认证对象不能为空");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected LoginTypeEnum obtainLoginType(JSONObject object) {
        Integer loginType = object.getInt(this.loginTypeParameter);
        return LoginTypeEnum.getByValue(loginType);
    }

    protected void setDetails(HttpServletRequest request,
            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setLoginTypeParameter(String loginTypeParameter) {
        Assert.hasText(loginTypeParameter, "loginTypeParameter must not be empty or null");
        this.loginTypeParameter = loginTypeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getLoginTypeParameter() {
        return loginTypeParameter;
    }

    class RequestWrapper extends HttpServletRequestWrapper {

        private final String body;

        public RequestWrapper(HttpServletRequest request) {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } catch (IOException ex) {

            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            body = stringBuilder.toString();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    body.getBytes());
            ServletInputStream servletInputStream = new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
            return servletInputStream;

        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        public String getBody() {
            return this.body;
        }
    }
}
