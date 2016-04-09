package com.dc.itcs.core.base.utils;

import com.dc.flamingo.core.utils.PropertiesUtils;

/**
 * 
 * @ClassName: ImageUtils
 * @Description: 图片处理工具类
 * @Create In 2014年9月17日 By lee
 */
public class ImageUtils {
	//图片路径标识KEY
	public static String IMAGE_URL_MATCH_KEY = "IMAGE_URL_MATCH_KEY";
	//WEB图片真实路径
	private static String WEB_URL = PropertiesUtils.getProperty("app.image.webPath");
	
	/**
	 * 将HTML中的图片真实路径更新为KEY
	 * @Methods Name formatImage
	 * @Create In 2014年9月17日 By lee
	 * @param html
	 * @return
	 */
	public static String urlToKey(String html){
		return html.replaceAll(WEB_URL, IMAGE_URL_MATCH_KEY);
	}
	
	/**
	 * 将HTML中的图片路径KEY更新为真实路径
	 * @Methods Name decodeImage
	 * @Create In 2014年9月18日 By lee
	 * @param html
	 * @return
	 */
	public static String keyToUrl(String html){
		return html.replaceAll(IMAGE_URL_MATCH_KEY, WEB_URL);
	}
}
