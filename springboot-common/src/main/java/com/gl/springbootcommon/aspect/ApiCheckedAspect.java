package com.gl.springbootcommon.aspect;

import com.gl.springbootcommon.annotation.ApiChecked;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ApiCheckedAspect {

    @Order(1) // Order 代表优先级，数字越小优先级越高
    //定义切入点
    @Pointcut("@annotation(com.gl.springbootcommon.annotation.ApiChecked)")
    public void checkedPoint() {}

    /**
     * 环绕通知,针对注解ApiChecked进行处理
     *
     * @param joinPoint
     * @param apiChecked
     * @return java.lang.Object
     **/
    @Around(value = "checkedPoint() && @annotation(apiChecked)")
    public Object doLogs(ProceedingJoinPoint joinPoint, ApiChecked apiChecked) throws Throwable {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();
        // 获取入参名和值
        Object[] param = joinPoint.getArgs();
        String[] names = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Class<?> targetCls = joinPoint.getTarget().getClass();
        String targetClsName = targetCls.getSimpleName();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], param[i]);
        }
        log.info("服务: {} , 功能: {} , 方法名: {} , 参数: {}", targetClsName, apiChecked.value(), methodName, map);

        //执行原有接口方法
        Object result = null;
        result = joinPoint.proceed();
        return result;
    }
}
