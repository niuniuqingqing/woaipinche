package com.woaipinche.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woaipinche.model.User;
import com.woaipinche.repository.UserRepository;

@Controller
public class GeRenController {
	
	@Autowired
	private UserRepository userRepository;
    
    @RequestMapping(value = "/geren", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> geren(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String phone = (String)request.getSession().getAttribute("phone");
    	if(phone==null){
    		modelMap.put("status", '2');//没有登录
    	}else{
    		modelMap.put("status", '1');//登录 返回账号信息
    		//查询账号信息
    		User user = userRepository.findOne(phone);
    		if(user==null || user.getId()==null){
    			modelMap.put("name","游客");
    			modelMap.put("phone", phone);
    			modelMap.put("isHuiyuan", '0');//不是
    		}else{
    			modelMap.put("name", user.getName());
    			modelMap.put("phone", user.getPhone());
    			modelMap.put("isHuiyuan", user.getIsHuiyuan());
    		}
    	}
        return modelMap;
    }
    
    
    
    @RequestMapping(value = "/denglu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> denglu(@RequestBody User user,HttpServletRequest request) {
    	String phone = user.getPhone();
    	request.getSession().setAttribute("phone", phone);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", '0');
        return modelMap;
    }
    
    
    @RequestMapping(value = "/tuichu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tuichu(@RequestBody User user,HttpServletRequest request) {
    	request.getSession().removeAttribute("phone");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", '0');
        return modelMap;
    }
    
    @RequestMapping(value = "/beizhu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> beizhu(@RequestBody User user,HttpServletRequest request) {
    	String phone = (String)request.getSession().getAttribute("phone");
    	user.setId(phone);
    	user.setPhone(phone);
    	userRepository.saveAndFlush(user);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", '0');
        return modelMap;
    }
    
    
}
