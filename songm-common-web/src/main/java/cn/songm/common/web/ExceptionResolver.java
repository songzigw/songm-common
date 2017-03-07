package cn.songm.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.songm.common.service.ServiceException;

public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOG = LoggerFactory
            .getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ServiceException.class.isAssignableFrom(ex.getClass())) {
                // 如果是业务异常类
                ServiceException se = (ServiceException) ex;

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errNotice", se.getErrNotice());
                return new ModelAndView("exception", map);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return null;
    }

}
