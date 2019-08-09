package com.woaipinche.controller;

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

import com.woaipinche.model.YuDing;
import com.woaipinche.repository.YuDingRepository;

@Controller
public class YuDingController {
	
	@Autowired
	private YuDingRepository yuDingRepository;
	
    @RequestMapping(value = "/yudingPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> yuding(@RequestBody YuDing yuDing,HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
		Pageable pageable = new PageRequest(0,50,new Sort(Direction.DESC,"set_out_date"));
        Page<YuDing> yuDingPage = yuDingRepository.findYuDingPointPage(yuDing.getStartPoint(),yuDing.getEndPoint(),pageable);
        List<YuDing> yuDings = yuDingPage.getContent();
        modelMap.put("value",yuDings);
        return modelMap;
    }
    
    @RequestMapping(value = "/insertYuding", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertYuding(@RequestBody YuDing yuDing,HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(yuDing.getId()==null){
        	yuDing.setId(UUID.randomUUID().toString());
        }
        yuDingRepository.saveAndFlush(yuDing);
        modelMap.put("status",'0');
        return modelMap;
    }
    
    
    @RequestMapping(value = "/wodefabu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> wodefabu(@RequestBody YuDing yuDing,HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
		Pageable pageable = new PageRequest(0,50,new Sort(Direction.DESC,"set_out_date"));
        Page<YuDing> yuDingPage = yuDingRepository.findWodefabuPage(yuDing.getPhone(),pageable);
        List<YuDing> yuDings = yuDingPage.getContent();
        modelMap.put("value",yuDings);
        return modelMap;
    }
    
    @RequestMapping(value = "/qxfb", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> qxfb(@RequestBody YuDing yuDing,HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        yuDingRepository.delete(yuDing);
        modelMap.put("status",'0');
        return modelMap;
    }
    
    
}
