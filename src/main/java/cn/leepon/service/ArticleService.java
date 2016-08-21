package cn.leepon.service;

import java.util.List;

import cn.leepon.po.Article;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月20日 下午10:17:46   
 */
public interface ArticleService {
	
	public void buildIndex();

	public List<Article> pageQuery(int start, int limit,String keyword);

	public List<Article> search(String keyword);  

}
