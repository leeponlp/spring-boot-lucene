package cn.leepon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.leepon.mapper.ArticleMapper;
import cn.leepon.po.Article;
import cn.leepon.service.ArticleService;
import cn.leepon.util.LuceneUtil;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月20日 下午10:18:03   
 */

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	ArticleMapper articleMapper;

	@Override
	public void buildIndex() {
		
		List<Article> list = articleMapper.findAll(); 
		List<Document> doclist = new ArrayList<>();
		for (Article article : list) {
			Document document = new Document();
			document.add(new Field("id", article.getId()+"", Store.YES, Index.NOT_ANALYZED));
			document.add(new Field("title", article.getTitle(), Store.YES, Index.ANALYZED));
			document.add(new Field("content", article.getContent(), Store.YES, Index.ANALYZED));
			document.add(new Field("status", article.getStatus()+"", Store.YES, Index.NOT_ANALYZED));
			doclist.add(document);
		}
		LuceneUtil.buildAllIndex(doclist); 
	}

	@Override
	public List<Article> pageQuery(int start, int limit,String keyword) {
		WildcardQuery query = new WildcardQuery(new Term("content", "*" + keyword + "*"));
		List<Document> doclist = LuceneUtil.pageQuery(query, start, limit);
		List<Article> list = new ArrayList<>();
		for (Document document : doclist) {
			Article article = new Article();
			article.setId(Integer.parseInt(document.get("id")));
			article.setTitle(document.get("title"));
			article.setContent(document.get("content"));
			article.setStatus(Integer.parseInt(document.get("status")));
			list.add(article);
		}
		return list;
	}

	@Override
	public List<Article> search(String keyword) {
		WildcardQuery query = new WildcardQuery(new Term("content", "*" + keyword + "*"));
		List<Document> rows = LuceneUtil.searchRows(query);
		List<Article> list = new ArrayList<>();
		for (Document document : rows) {
			Article article = new Article();
			article.setId(Integer.parseInt(document.get("id")));
			article.setTitle(document.get("title"));
			article.setContent(document.get("content"));
			article.setStatus(Integer.parseInt(document.get("status")));
			list.add(article);
		}
		return list;
	}
	

}
