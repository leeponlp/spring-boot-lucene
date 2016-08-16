package cn.leepon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.leepon.mapper.TChinaCityMapper;
import cn.leepon.po.TChinaCity;
import cn.leepon.service.CityIndexService;
import cn.leepon.service.CityService;
import cn.leepon.util.LuceneUtil;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年4月18日 下午5:05:16   
 */
@Service
public class CityServiceImpl implements CityService {
	
	private static Logger logger = Logger.getLogger(CityServiceImpl.class);

	@Autowired
	TChinaCityMapper tChinaCityMapper;
	
	@Autowired
	CityIndexService cityIndexService;
	
	@Override
	public List<TChinaCity> listCity() {
		
		List<TChinaCity> list = tChinaCityMapper.listCity();
		//建立索引
		if (list !=null && list.size()>0) {
			
			cityIndexService.buildIndex(list);
		}
		return list;
		
	}
	

	@Override
	public List<TChinaCity> queryCityByName(String name) {
		
		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36,new String[]{"name"},LuceneUtil.getAnalyzer());
		Query query = null; 
		List<TChinaCity> list = null;
		try {
			query = parser.parse(name);
			list = executeQuery(query); 
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return list;
	}


	
	
    @Override
	public List<TChinaCity> queryAllByLucene(){
		//匹配 *
		Query query = new MatchAllDocsQuery();
		List<TChinaCity> list = executeQuery(query);
		return list;
	}
	

	/**
	 * 
	 * @Title: executeQuery 
	 * @Description: TODO <执行索引查询>
	 * @param @param query
	 * @param @return 
	 * @return List<TChinaCity>  
	 * @throws
	 */
	private static List<TChinaCity> executeQuery(Query query){
		List<TChinaCity> list = null;
		IndexSearcher indexSearcher = null;
			try {
				indexSearcher = LuceneUtil.getIndexSearcher();
				TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
				logger.info("搜索结果 " + topDocs.totalHits + "条");
				list = new ArrayList<>();
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					Document document = indexSearcher.doc(scoreDoc.doc);
					TChinaCity city = new TChinaCity();
					city.setId(Integer.parseInt(document.get("id")));
					city.setName(document.get("name")); 
					city.setParentId(Integer.parseInt(document.get("parentId"))); 
					city.setSort(Integer.parseInt(document.get("sort"))); 
					city.setStatus(Integer.parseInt(document.get("status"))); 
					city.setCreateTime(Integer.parseInt(document.get("createTime"))); 
					city.setUpdateTime(Integer.parseInt(document.get("updateTime"))); 
					list.add(city);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		
		
		return list;

	}
}
