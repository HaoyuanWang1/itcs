package com.dc.itcs.core.web.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.data.domain.Page;
import com.dc.flamingo.core.utils.StrUtils;

@SuppressWarnings("serial")
public class PagerTag extends TagSupport {

	private Page<Object> page; // 分页信息
	private Map<String, Object> params; // 查询参数信息
	private String url; // 请求地址
	private String loadTarget; // 异步加载信息父元素jquery选择器表达式
	private String rowsArr; // 每页显示条数种类，逗号隔开
	private String className; // 分页器特殊样式，大小，位置等
	private Integer viewPages; // 显示的数字页码个数，一般用于较窄页面分页展示

	private int curPage; // 当前页
	private int totalPage; // 总页数
	private int pageSize; // 每页显示条数
	private String cutUrl; // 无分页参数路径
	private String fullUrl; // 全路径,无分页传参

	private static final String dotted = "<li class=\"dotted\"><span>...</span></li>"; // 点点...

	public int doStartTag() throws JspException {
		initFullUrl();
		initPageInfo();
		
		StringBuffer uri = new StringBuffer();
		JspWriter out = pageContext.getOut();
		try {
			
			if (totalPage > 1) {
				
				uri.append("<div");
				if (StrUtils.isEmpty(className)) {
					uri.append(" class=\"pagination\"");
				} else {
					uri.append(" class=\"pagination " + className + "\"");
				}
				
				uri.append(" ><ul>");


				if (null == viewPages || viewPages < 1) { // 默认完整页码显示
					uri.append(this.getPrevPage());

					if (totalPage <= 10) {
						for (int i = 1; i <= totalPage; i++) {
							uri.append(this.getPageLink(i));
						}
					} else {
						uri.append(this.getPageLink(1));
						uri.append(this.getPageLink(2));
						uri.append(this.getPageLink(3));
						if (curPage < 8) {
							uri.append(this.getPageLink(4));
							uri.append(this.getPageLink(5));
							uri.append(this.getPageLink(6));
							uri.append(this.getPageLink(7));
							uri.append(this.getPageLink(8));
							uri.append(dotted);
						} else if (curPage >= 8 && curPage <= (totalPage - 8)) {
							uri.append(dotted);
							uri.append(this.getPageLink(curPage - 2));
							uri.append(this.getPageLink(curPage - 1));
							uri.append(this.getPageLink(curPage));
							uri.append(this.getPageLink(curPage + 1));
							uri.append(this.getPageLink(curPage + 2));
							uri.append(dotted);
						} else if (curPage > (totalPage - 8)) { // 最后7页
							uri.append(dotted);
							uri.append(this.getPageLink(totalPage - 7));
							uri.append(this.getPageLink(totalPage - 6));
							uri.append(this.getPageLink(totalPage - 5));
							uri.append(this.getPageLink(totalPage - 4));
							uri.append(this.getPageLink(totalPage - 3));
						}
						uri.append(this.getPageLink(totalPage - 2));
						uri.append(this.getPageLink(totalPage - 1));
						uri.append(this.getPageLink(totalPage));
					}

					uri.append(this.getNextPage());
				} else { // 规定显示页码个数
				
					uri.append(this.getSimplePrevPage());
					
					if (totalPage <= viewPages) {
						for (int i = 1; i <= totalPage; i++) {
							uri.append(this.getPageLink(i));
						}
					} else {
						int half = (int)Math.floor(viewPages / 2);
						
						if (curPage <= half) {
							for (int i = 1; i <= viewPages; i++) {
								uri.append(this.getPageLink(i));
							}
						} else if (curPage >= (totalPage - half)) {
							for (int i = totalPage - viewPages + 1; i <= totalPage; i++) {
								uri.append(this.getPageLink(i));
							}
						} else {
							for (int i = curPage - half; i < viewPages + (curPage - half); i++) {
								uri.append(this.getPageLink(i));
							}
						}
					}
					
					uri.append(this.getSimpleNextPage());
				}

				uri.append("</ul>");
	
				// 每页个数[@hujx]
				if (null != rowsArr && !"".equals(rowsArr)) {
					uri.append("<div class='size-ctrl'>");
	
					String[] strArr = StrUtils.split(rowsArr, ",");
	
					for (int i = 0; i < strArr.length; i++) {
						String ps = strArr[i];
						String urlByRows = cutUrl + "pageSize=" + ps
								+ "&page=1";
	
						if (pageSize == Integer.valueOf(ps)) {
							uri.append("<span class='current'>" + ps + "</span>");
						} else {
							uri.append("<a class=\"page-goto\" href=\"" + urlByRows + "\"");
							if (StrUtils.isNotEmpty(loadTarget)) {
								uri.append(" data-target=\"" + loadTarget + "\"");
							}
							uri.append(" >" + ps + "</a>");
						}
	
						if (i < strArr.length - 1) {
							uri.append("|");
						}
					}
					uri.append("条/页</div>");
				}
	
				uri.append("</div>");
			}
			out.print(uri.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	private void initFullUrl() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext
				.getRequest();
		String requestUrl = httpServletRequest.getRequestURL().toString();
		String contextPath = httpServletRequest.getContextPath();
		requestUrl = requestUrl.split(contextPath)[0];
		cutUrl = requestUrl + contextPath + url;
		StringBuffer paramBuffer = new StringBuffer();

		if (params != null && !params.isEmpty()) {
			for (String paramName : params.keySet()) {
				
				if ("page".equals(paramName)) {
					continue;
				}
				if ("pageSize".equals(paramName)) {
					continue;
				}
				
				paramBuffer.append(paramName + "="
						+ params.get(paramName).toString() + "&");
			}
		}
		
		if (cutUrl.indexOf("?") > -1) {
			cutUrl += "&" + paramBuffer.toString();
		} else {
			cutUrl = cutUrl + "?" + paramBuffer.toString();
		}
		
	}

	private void initPageInfo() {
		curPage = page.getNumber() + 1;
		pageSize = page.getSize();
		totalPage = page.getTotalPages();
		fullUrl = cutUrl + "pageSize=" + pageSize + "&page=";
	}

	private String getPageLink(int targetPageNo) {
		if (curPage == targetPageNo) {
			return "<li class=\"active\"><span>" + targetPageNo + "</span></li>";
		} else {
			if (StrUtils.isNotEmpty(loadTarget)) {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + targetPageNo + "\" data-target=\"" + loadTarget + "\" data-page=\"" + targetPageNo + "\">"
					+ targetPageNo + "</a></li>";
			} else {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + targetPageNo + "\" data-page=\"" + targetPageNo + "\">"
						+ targetPageNo + "</a></li>";
			}
		}
	}

	private String getPrevPage() {
		if (curPage <= 1) {
			return "";
		} else {
			if (StrUtils.isNotEmpty(loadTarget)) {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + 1 + "\"  data-target=\"" + loadTarget + "\"  data-page=\"1\">首页</a></li>" 
						 + "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage - 1) + "\" data-target=\"" + loadTarget + "\" data-page=\"" + (curPage - 1) + "\">上一页</a></li>";
			} else {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + 1 + "\" data-page=\"1\">首页</a></li>" 
				 + "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage - 1) + "\" data-page=\"" + (curPage - 1) + "\">上一页</a></li>";
			}
		}
	}
	
	private String getSimplePrevPage() {
		if (curPage <= 1) {
			return "<li class=\"disabled\"><span>«</span></li>";
		} else {
			if (StrUtils.isNotEmpty(loadTarget)) {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage - 1) + "\" data-target=\"" + loadTarget + "\" data-page=\"" + (curPage - 1) + "\">«</a></li>";
			} else {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage - 1) + "\" data-page=\"" + (curPage - 1) + "\">«</a></li>";
			}
		}
	}

	private String getNextPage() {
		if (curPage == totalPage) {
			return "";
		} else {
			if (StrUtils.isNotEmpty(loadTarget)) {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage + 1) + "\" data-target=\"" + loadTarget + "\" data-page=\"" + (curPage + 1) + "\">下一页</a></li>" 
						+ "<li><a  class=\"page-goto\"href=\"" + fullUrl + totalPage + "\" data-target=\"" + loadTarget + "\" data-page=\"" + totalPage + "\">尾页</a></li>";
			} else {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage + 1) + "\" data-page=\"" + (curPage + 1) + "\">下一页</a></li>" 
						+ "<li><a  class=\"page-goto\"href=\"" + fullUrl + totalPage + "\" data-page=\"" + totalPage + "\">尾页</a></li>";
			}
		}
	}

	private String getSimpleNextPage() {
		if (curPage == totalPage) {
			return "<li class=\"disabled\"><span>»</span></li>";
		} else {
			if (StrUtils.isNotEmpty(loadTarget)) {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage + 1) + "\" data-target=\"" + loadTarget + "\" data-page=\"" + (curPage + 1) + "\">»</a></li>";
			} else {
				return "<li><a class=\"page-goto\" href=\"" + fullUrl + (curPage + 1) + "\" data-page=\"" + (curPage + 1) + "\">»</a></li>";
			}
		}
	}

	public Page<Object> getPage() {
		return page;
	}

	public void setPage(Page<Object> page) {
		this.page = page;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getLoadTarget() {
		return loadTarget;
	}

	public void setLoadTarget(String loadTarget) {
		this.loadTarget = loadTarget;
	}

	public String getRowsArr() {
		return rowsArr;
	}

	public void setRowsArr(String rowsArr) {
		this.rowsArr = rowsArr;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getViewPages() {
		return viewPages;
	}

	public void setViewPages(Integer viewPages) {
		this.viewPages = viewPages;
	}

	public void release() {
		page = null;
		params = null;
		url = null;
		loadTarget = null;
		rowsArr = null;
		className = null;
		viewPages = null;
		cutUrl = null;
		fullUrl = null;
		super.release();
	}

}