package com.dc.itcs.security.shiro;
//package com.dc.itil.security.shiro;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.shiro.config.Ini;
//import org.apache.shiro.config.Ini.Section;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.dc.flamingo.core.utils.StrUtils;
//import com.dc.itil.security.entity.Resource;
//import com.dc.itil.security.entity.RoleResource;
//import com.dc.itil.security.service.ResourceService;
//import com.dc.itil.security.service.RoleResourceService;
//
//public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{
//	@Autowired
//    private ResourceService resourceService;
//	@Autowired
//    private RoleResourceService roleResourceService;
//
//    private String filterChainDefinitions;
//    /**
//     * 默认premission字符串
//     */
//    public static final String PREMISSION_STRING="perms[\"{0}\"]";
//
//
//    public Section getObject() throws BeansException {
//        //获取所有Resource
//        List<Resource> list = resourceService.findAll();
//        List<RoleResource> rrList = roleResourceService.findAll();
//        Map<String,Set<String>> rolePermMap = new HashMap<String,Set<String>>();
//        for(RoleResource rr : rrList){
//        	String pageUrl = rr.getResource().getPageUrl();
//        	Set<String> permRoles = rolePermMap.get(pageUrl);
//        	if(permRoles==null){
//        		permRoles = new HashSet<String>();
//        	}
//        	permRoles.add(rr.getRole().getRid());
//        	rolePermMap.put(pageUrl, permRoles);
//        }
//        Ini ini = new Ini();
//        //加载默认的url
//        ini.load(filterChainDefinitions);
//        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
//        //循环权限项的url,逐个添加到section中。section就是filterChainDefinitionMap,
//        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
//        for (Resource at : list) {
//            //如果不为空值添加到section中
//            if(!at.isNoPage()) {
//            	if(Resource.TYPE_ANON==at.getType()){
//            		section.put(at.getPageUrl(),  "anon");
//            	}else if(Resource.TYPE_ROLE==at.getType()){
//            		section.put(at.getPageUrl(),  "roles["+StrUtils.join(rolePermMap.get(at.getPageUrl()), ",")+"]");
//            	}else{
//            		section.put(at.getPageUrl(),  "authc");
//            	}
//            }
//        }
//        section.put("/**",  "authc");
//        return section;
//    }
//
//    /**
//     * 通过filterChainDefinitions对默认的url过滤定义
//     * 
//     * @param filterChainDefinitions 默认的url过滤定义
//     */
//    public void setFilterChainDefinitions(String filterChainDefinitions) {
//        this.filterChainDefinitions = filterChainDefinitions;
//    }
//
//    public Class<?> getObjectType() {
//        return this.getClass();
//    }
//
//    public boolean isSingleton() {
//        return false;
//    }
//
//}
