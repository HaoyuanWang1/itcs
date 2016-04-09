package com.dc.itcs.security.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.security.domain.ResourceNode;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.service.ResourceNodeService;
import com.dc.itcs.security.service.ResourceService;
import com.dc.itcs.security.shiro.ShiroFilerChainManager;

/**
 * 资源授权控制器
 * @ClassName: ResourceController
 * @Description: TODO
 * @Create In 2014年10月23日 By lee
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ResourceNodeService resourceNodeService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	
	/**
	 * 资源树形展示页面
	 * @Methods Name menuTree
	 * @Create In 2014年10月23日 By lee
	 * @return
	 */
	@RequestMapping("/menuTree")
    public ModelAndView menuTree() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/admin/security/menu_tree");
        return mav;
    }
	
	/**
	 * 资源JSON数据
	 * @Methods Name menuJson
	 * @Create In 2014年10月23日 By lee
	 * @return
	 */
	@RequestMapping(value="/menuJson")
    @ResponseBody
    public AjaxResult menuJson(){
    	ResourceNode root = ResourceNode.getRootNode();
    	root.setChildren(resourceNodeService.findMenuByParent(ResourceNode.ROOT_ID));
        return AjaxResult.objectResult(root);
    }
	/**
	 * 树形显示页
	 * @return
	 */
	@RequestMapping("/tree")
    public ModelAndView tree() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/admin/security/resource_tree");
        return mav;
    }
	/**
	 * 树形数据结构
	 * @return
	 */
    @RequestMapping(value="/rootJson")
    @ResponseBody
    public AjaxResult rootJson(){
    	ResourceNode root = ResourceNode.getRootNode();
    	root.setChildren(resourceNodeService.findByParent(ResourceNode.ROOT_ID));
        return AjaxResult.objectResult(root);
    }
    /**
	 * 获取资源数据
	 * @return
	 */
    @RequestMapping(value="/resourceJson/{id}")
    @ResponseBody
    public AjaxResult resourceJson(@PathVariable Long id){
    	Resource resource = resourceService.findById(id);
        return AjaxResult.objectResult(resource);
    }
    /**
     * 删除资源
     * @param id
     * @return
     */
    @RequestMapping(value="/removeResource/{id}")
    @ResponseBody
    public AjaxResult removeResourceAndRelate(@PathVariable Long id){
        if(id==null||id==0){
            return AjaxResult.successResult();
        }
        resourceService.removeResourceAndRelate(id);
        return AjaxResult.successResult();
        
    }
	@RequestMapping("/page")
	public ModelAndView page(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		Pageable pageable = webParamInfo.getPageable();
		Page<Resource> page = resourceService.findByPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/admin/security/resource_list");
		return mav;
	}

	/**
	 * 保存资源
	 * @param resource
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(Resource resource){
		resourceService.save(resource);
		return AjaxResult.successResult();
	}
	
	/**
	 * 重置资源拦截
	 * @return
	 */
	@RequestMapping("/reloadFilter")
	@ResponseBody
	public AjaxResult reloadFilter(){
		shiroFilerChainManager.reloadFilterChains();
		return AjaxResult.successResult();
	}
}

