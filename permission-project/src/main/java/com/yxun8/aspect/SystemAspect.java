package com.yxun8.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxun8.domain.Employees;
import com.yxun8.domain.Systemlog;
import com.yxun8.mapper.EmployeesMapper;
import com.yxun8.utils.RequestUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*<insert id="insert" parameterType="com.yxun8.domain.Systemlog">
        insert into `systemlog` (username,loptime,lip,lmethod,lparams)
        values (#{username},#{loptime},#{lip},#{lmethod},#{lparams})
</insert>
<select id="selectAll" resultType="com.yxun8.domain.Systemlog">
        select * from `systemlog`;
</select>*/


/**
 * 日志
 */
public class SystemAspect {

    @Autowired
    private EmployeesMapper employeesMapper;

    public void log(JoinPoint joinPoint) throws JsonProcessingException {
        Systemlog systemlog = new Systemlog();
        Subject subject = SecurityUtils.getSubject();
        Employees employee = (Employees) subject.getPrincipal();
        /*设置用户名*/
        if (subject.getPrincipal() == null){
            return;
        }else {
            systemlog.setUsername(employee.getUsername());
        }
        /*设置时间*/
        systemlog.setLoptime(new Date());
        /*设置ip*/
        //把本地线程取出来
        HttpServletRequest request = RequestUtil.getLocal();
        if (request != null){
            String ip = request.getRemoteAddr();  //获取ip地址
            systemlog.setLip(ip);
        }

        /*设置执行过的方法*/
        //1.先获取执行目标的全路径
        String name = joinPoint.getTarget().getClass().getName();
        //2.获取方法名
        String method = joinPoint.getSignature().getName();
        String fun = name + ":" + method;
        systemlog.setLmethod(fun);

        /*设置执行参数*/
        String param = new ObjectMapper().writeValueAsString(joinPoint.getArgs());
        systemlog.setLparams(param);
        /*保存日志信息*/
        employeesMapper.saveLog(systemlog);


    }
}
