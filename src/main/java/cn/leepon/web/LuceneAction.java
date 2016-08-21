package cn.leepon.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.leepon.po.Article;
import cn.leepon.service.ArticleService;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月20日 下午10:40:42   
 */

@Controller
@RequestMapping("/lucene")
public class LuceneAction {
	
	@Autowired
	ArticleService articleService;
	
	@RequestMapping("/build")
	public String buildIndex(){
		articleService.buildIndex();
		return "index";
	}
	
	@RequestMapping("/query/{keyword}")
	@ResponseBody
	public List<Article> search(@PathVariable String keyword){
		
		return articleService.search(keyword);
	}
	
	@RequestMapping("/query/{start}/{limit}/{keyword}")
	@ResponseBody
	public List<Article> pageQuery(@PathVariable int start,@PathVariable int limit,@PathVariable String keyword){
		
		return articleService.pageQuery(start,limit,keyword);
	}

}
