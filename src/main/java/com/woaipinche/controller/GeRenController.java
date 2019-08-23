package com.woaipinche.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woaipinche.model.Pinlun;
import com.woaipinche.model.User;
import com.woaipinche.repository.PinlunRepository;
import com.woaipinche.repository.UserRepository;

@Controller
public class GeRenController {
	
	@Autowired
	private UserRepository userRepository;
    
	@Autowired
	private PinlunRepository pinlunRepository;
    
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
    public Map<String, Object> denglu(@RequestBody Map<String, Object> map,HttpServletRequest request) {
    	//验证码是否正确
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	String phone = (String) map.get("phone");
    	String yzm = (String) map.get("yzm");
    	String yzmSession = String.valueOf(request.getSession().getAttribute("yzm"));
    	if(yzmSession.equals(yzm)){
    		 request.getSession().setAttribute("phone",phone);
    		 modelMap.put("status", '0');
    	}else{
    		 modelMap.put("status", '1');
    	}
        return modelMap;
    }
    
    //获取验证码
    @RequestMapping(value = "/yzm", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> yzm(@RequestBody User user,HttpServletRequest request) {
    	String phone = user.getPhone();
    	int yzm = (int)((Math.random()*9+1)*1000);
    	request.getSession().setAttribute("yzm", yzm);
    	//发送短信验证码
    	SendMessage ms = new SendMessage();
    	String[] params = {String.valueOf(yzm),"1"};
		ms.send(params,phone);
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
    
    
    @RequestMapping(value = "/wodechengyuan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> wodechengyuan(@RequestBody User user,HttpServletRequest request) {
    	 String phone = (String)request.getSession().getAttribute("phone");
    	 Map<String, Object> modelMap = new HashMap<String, Object>();
 		 Pageable pageable = new PageRequest(0,50,new Sort(Direction.DESC,"id"));
         Page<User> userPage = userRepository.findWodeChengyuanPage(phone,pageable);
         List<User> users = userPage.getContent();
         modelMap.put("value",users);
         return modelMap;
    }
    
    
    @RequestMapping(value = "/fabu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fabu(@RequestBody Pinlun pinlun,HttpServletRequest request) {
    	String phone = (String)request.getSession().getAttribute("phone");
    	pinlun.setId(UUID.randomUUID().toString());
    	pinlun.setUserId(phone);
    	pinlun.setContent(pinlun.getContent());
    	System.out.println(new Date());
    	System.out.println(new Date());
    	pinlun.setFabuDate(new Date());
    	pinlunRepository.saveAndFlush(pinlun);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", '0');
        return modelMap;
    }
    
    
    @RequestMapping(value = "/pinlun", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pinlun(HttpServletRequest request) {
    	 Map<String, Object> modelMap = new HashMap<String, Object>();
    	String phone = (String)request.getSession().getAttribute("phone");
    	if(phone==null){
    		modelMap.put("status", '2');//没有登录
    	}else{
    		Pageable pageable = new PageRequest(0,50,new Sort(Direction.DESC,"fabu_date"));
            Page<Pinlun> pinlunPage = pinlunRepository.findPinlunPage(pageable);
            List<Pinlun> pinluns = pinlunPage.getContent();
            modelMap.put("value",pinluns);
    	}
        return modelMap;
    }
}
