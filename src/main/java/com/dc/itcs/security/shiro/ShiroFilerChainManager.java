package com.dc.itcs.security.shiro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.entity.RoleResource;
import com.dc.itcs.security.service.ResourceService;
import com.dc.itcs.security.service.RoleResourceService;

/**
 * 安全过滤链管理器
 * @author lee
 *
 */
@Component("shiroFilerChainManager")
@Transactional(readOnly=true)
public class ShiroFilerChainManager {
	private static final Logger log = LoggerFactory.getLogger(ShiroFilerChainManager.class);
	@Autowired
    private ResourceService resourceService;
	@Autowired
    private RoleResourceService roleResourceService;
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	private static String filterChainDefinitions = "/login = authc \r\n /logout = logout \r\n /static/** = anon \r\n /uploadImages/** = anon \r\n";
	
	//注意/r/n前不能有空格
    private static final String CRLF= "\r\n";

    /**
     * 加载拦截信息
     * @return
     */
	public String loadFilterChainDefinitions() {
		List<Resource> rlist = resourceService.findAll();
		List<RoleResource> rrList = roleResourceService.findAll();
		Map<String, Set<String>> rolePermMap = new HashMap<String, Set<String>>();
		for (RoleResource rr : rrList) {
			String pageUrl = rr.getResource().getPageUrl();
			Set<String> permRoles = rolePermMap.get(pageUrl);
			if (permRoles == null) {
				permRoles = new HashSet<String>();
			}
			permRoles.add(rr.getRole().getRid());
			rolePermMap.put(pageUrl, permRoles);
		}
		StringBuffer cdBuffer = new StringBuffer();
		cdBuffer.append(filterChainDefinitions);
		cdBuffer.append(CRLF);
		for (Resource resource : rlist) {
			if (!resource.isNoPage()) {
				if (Resource.TYPE_ANON == resource.getType()) {
					cdBuffer.append(resource.getPageUrl()+"= anon");
					cdBuffer.append(CRLF);
				} else if (Resource.TYPE_ROLE == resource.getType()) {
					cdBuffer.append(resource.getPageUrl()+"= roles[" + StrUtils.join(rolePermMap.get(resource.getPageUrl()), ",") + "]");
					cdBuffer.append(CRLF);
				}
			}
		}
		cdBuffer.append("/** = authc");
		return cdBuffer.toString();
	}
	/**
	 * 重载过滤链
	 */
	public void reloadFilterChains() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

		// 清空老的权限控制
		manager.getFilterChains().clear();

		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
		// 重新构建生成
		Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}
		log.info("=======重新载入安全拦截信息=====");
	}

}
