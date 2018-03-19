package me.learn.web.architecture.myservlet;


import me.learn.web.architecture.util.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

import com.alibaba.fastjson.JSON;
import me.learn.web.architecture.component.Handler;
import me.learn.web.architecture.component.Param;
import me.learn.web.architecture.model.Data;
import me.learn.web.architecture.model.View;

/**
 * web
 * me.learn.web.architecture.myservlet
 * Created by kukuxiahuni on 2018/3/16.
 */

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    /**
     * Receives standard HTTP requests from the public
     * <code>service</code> method and dispatches
     * them to the <code>do</code><i>XXX</i> methods defined in
     * this class. This method is an HTTP-specific version of the
     * {@link Servlet#service} method. There's no
     * need to override this method.
     *
     * @param req  the {@link HttpServletRequest} object that
     *             contains the request the client made of
     *             the servlet
     * @param resp the {@link HttpServletResponse} object that
     *             contains the response the servlet returns
     *             to the client
     * @throws IOException      if an input or output error occurs
     *                          while the servlet is handling the
     *                          HTTP request
     * @throws ServletException if the HTTP request
     *                          cannot be handled
     * @see Servlet#service
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if (Objects.nonNull(handler)) {
            Class<?> controllerClass = handler.getControllerClass();

            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paraMap = new HashMap<>();

            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paraMap.put(paramName, paramValue);

                String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));

                if (StringUtils.isNotEmpty(body)) {
                    String[] params = StringUtils.split(body, "&");

                    if (Objects.nonNull(params) && params.length > 0) {

                        Arrays.stream(params).forEach(param -> {
                            String[] array = StringUtils.split(param, "=");
                            if (Objects.nonNull(array) && array.length == 2) {
                                paraMap.put(array[0], array[1]);
                            }
                        });

                    }

                }
            }

            Param param = new Param(paraMap);

            //调用Action方法
            Method method = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);

            if (result instanceof View) {
                View view = (View) result;

                String path = view.getPath();

                if (StringUtils.isNotEmpty(path)) {

                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();

                        model.entrySet().stream().forEach(entry->{
                            req.setAttribute(entry.getKey(), entry.getValue());

                        });

                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }

                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();

                if (Objects.nonNull(model)) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = resp.getWriter();
                    String json = JSON.toJSONString(model);
                    printWriter.write(json);
                    printWriter.flush();
                    printWriter.close();

                }
            }

        }
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.  See {@link Servlet#init}.
     * <p>
     * <p>This implementation stores the {@link ServletConfig}
     * object it receives from the servlet container for later use.
     * When overriding this form of the method, call
     * <code>super.init(config)</code>.
     *
     * @param config the <code>ServletConfig</code> object
     *               that contains configutation
     *               information for this servlet
     * @throws ServletException if an exception occurs that
     *                          interrupts the servlet's normal
     *                          operation
     * @see
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        //初始化化相关helper类
        HelperLoader.init();

        //获取servletContext对象
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration servletRegistration = servletContext.getServletRegistration("jsp");

        servletRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认servlet

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }
}
