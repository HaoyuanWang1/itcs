//package com.dc.itcs.core.base.utils;
//
//import java.util.Map;
//
//import com.dc.flamingo.core.cache.FlamingoCacheManager;
//import com.dc.flamingo.core.context.ContextHolder;
//import com.google.common.collect.Maps;
//
///**
// * 缓存处理工具
// * @author lee
// *
// */
//public final class CacheUtil {
//	private static final FlamingoCacheManager cacheManager;
//	private CacheUtil(){}	//工具类不允许创建实例
//	private static ThreadLocal<Map<String,Object>> threadCache = new ThreadLocal<Map<String,Object>>();
//	private static final String THREAD_CACHE_NULL = "THREADCACHE_THIS_IS_NULL";
//	static{
//		cacheManager = ContextHolder.getBean(FlamingoCacheManager.class);
//	}
//
//	/**
//	 * 将指定key的对象压入缓存，并指定到某个组中
//	 * @param group 组名称，比如workflow
//	 * @param key，缓存的key，比如流程id
//	 * @param value 缓存的对象
//	 */
//	public static void put(String group,String key,Object value){
//		cacheManager.put(group, key, value);
//	}
//	/**
//	 * 从缓存中清除指定某个组的某个key的对象
//	 * @param group 组名称，比如workflow
//	 * @param key，缓存的key，比如流程id
//	 */
//	public static void remove(String group,String key){
//		cacheManager.remove(group, key);
//	}
//	
//
//	/**
//	 * 从缓存中清除某个组的所有对象
//	 */
//	public static void removeGroup(String group){
//		cacheManager.removeGroup(group);
//	}
//	
//	/**
//	 * 从缓存中清除所有对象
//	 */
//	public static void removeAll(){
//		cacheManager.removeAll();
//	}
//	
//
//	/**
//	 * 通过指定key从缓存中取出该对象，如果取不到，则返回null
//	 * @param group 组名称，比如workflow
//	 * @param key，缓存的key，比如流程id
//	 * @return
//	 */
//	public static Object get(String group,String key) {
//		Object result = null;
//		boolean isThread = threadCache.get()!=null;
//		if(isThread){
//			Map<String,Object> thisCache = threadCache.get();
//			result = thisCache.get(group+"_"+key);
//			if(result==null){
//				result = cacheManager.get(group, key);
//				if(result==null){
//					thisCache.put(group+"_"+key, THREAD_CACHE_NULL);
//				}else{
//					thisCache.put(group+"_"+key, result);
//				}
//			}else{
//				if(THREAD_CACHE_NULL.equals(result)){
//					result = null;
//				}
//			}
//		}else{
//			result = cacheManager.get(group, key);
//		}
//		return result;
//	}
//	public static void createLocalCache(){
//		threadCache.remove();
//		Map<String,Object> thisCache = Maps.newConcurrentMap();
//		threadCache.set(thisCache);
//	}
//	
//}
