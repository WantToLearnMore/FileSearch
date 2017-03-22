package com.search.aop;

import com.search.annotation.Authrity;
import com.search.po.User;
import com.search.utils.SesReqRespUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by ASUS! on 2017/2/19.
 */
@Aspect
@Service
public class AuthrityCheck {

    /**
     *利用Aop拦截带有Authrity注解的方法
     * 通过验证方法需要的权限和用户的权限判定是否能访问该方法
     * */
    @Around("@annotation(authrity)")
    public Object checkAuthrity(ProceedingJoinPoint joinPoint, Authrity authrity) throws Throwable {
        Object[] args=joinPoint.getArgs();
        for(Object arg:args){
            System.out.println(String.valueOf(arg));
        }

        //obtain the value of Annotation
        String isNeedAuthrity=authrity.authrity().toString();
        int privilege=authrity.privilege();

        if(isNeedAuthrity.equals("NoAuthrity")){
            HttpSession session= SesReqRespUtils.getCurrentSession();
            User user= (User) session.getAttribute("user");
            if(user==null){
                return "没有登录";
            }
            return  joinPoint.proceed();
        }else if(isNeedAuthrity.equals("Validate")){
            HttpSession session=  SesReqRespUtils.getCurrentSession();
            User user= (User) session.getAttribute("user");
            if(user==null){
               return "没有登录";
            }else if(privilege>user.getPrivilege()){
                return "没有权限";
            }
           return  joinPoint.proceed();
        }
        else  if(isNeedAuthrity.equals("NoValidate")){
            return  joinPoint.proceed();
        }

        return new String("没有权限进行这项操作"+"没获取到注解");
    }

}
