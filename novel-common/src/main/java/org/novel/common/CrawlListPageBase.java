 /**  
 *@Description:     
 */ 
package org.novel.common;  

import java.util.HashMap;
import java.util.List;

import org.novel.common.utils.RegexUtil;
  
public abstract class CrawlListPageBase extends CrawlBase{
	
	//当前页面URL地址
	private String pageUrl;
	
	public CrawlListPageBase(String pageUrl, String charsetName) throws Exception {
		readPageByGet(pageUrl, null, charsetName);
		this.pageUrl = pageUrl;
	}
	
	public CrawlListPageBase(String pageUrl, String charsetName, HashMap<String, String> params) throws Exception {
		readPageByGet(pageUrl, params, charsetName);
		this.pageUrl = pageUrl;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取下一跳的地址，组装到数组中
	 */
	public List<String> getPageUrl() {
		return RegexUtil.getArrayList(getPageSourceCode(), getUrlRegexStr(), this.pageUrl, getUrlRegexStrNum());
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 提取的内容正则表达式
	 */
	public abstract String getUrlRegexStr();
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 提取的内容在正则中的位置
	 */
	public abstract int getUrlRegexStrNum();
}
