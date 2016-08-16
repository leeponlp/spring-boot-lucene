package cn.leepon.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.stereotype.Service;
import cn.leepon.po.TChinaCity;
import cn.leepon.service.CityIndexService;
import cn.leepon.util.LuceneUtil;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月15日 上午10:56:04   
 */
@Service
public class CityIndexServiceImpl implements CityIndexService{
	
	private static Logger logger = Logger.getLogger(CityIndexServiceImpl.class);
	
	@Override
	public void buildIndex(TChinaCity city) {
		
		Document document = new Document();
		document.add(new Field("id", city.getId()+"", Store.YES,Index.NOT_ANALYZED));
		document.add(new Field("name", city.getName(),Store.YES,Index.ANALYZED));
		document.add(new Field("parentId", city.getParentId()+"",Store.YES,Index.NOT_ANALYZED));
		document.add(new Field("sort", city.getSort()+"",Store.YES,Index.NOT_ANALYZED));
		document.add(new Field("status", city.getStatus()+"",Store.YES,Index.NOT_ANALYZED));
		document.add(new Field("createTime", city.getCreateTime()+"",Store.YES,Index.NOT_ANALYZED));
		document.add(new Field("updateTime", city.getUpdateTime()+"",Store.YES,Index.NOT_ANALYZED));
		
		IndexWriter writer = LuceneUtil.getIndexWriter();
		try {
			writer.addDocument(document);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	
	@Override
	public void buildIndex(List<TChinaCity> list){
		
		List<Document> doclist = new ArrayList<>();
		
		for (TChinaCity tChinaCity : list) {
			
			Document document = new Document();
			document.add(new Field("id", tChinaCity.getId()+"", Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("name", tChinaCity.getName(),Store.YES,Index.ANALYZED));
			document.add(new Field("parentId", tChinaCity.getParentId()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("sort", tChinaCity.getSort()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("status", tChinaCity.getStatus()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("createTime", tChinaCity.getCreateTime()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("updateTime", tChinaCity.getUpdateTime()+"",Store.YES,Index.NOT_ANALYZED));
			
			doclist.add(document);
			
		}
		IndexWriter writer = LuceneUtil.getIndexWriter();
		for (Document document : doclist) {
			try {
				writer.addDocument(document);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage()); 
			e.printStackTrace();
		}

		
	}

	
	
	@Override
	public void deleteAllIndex(){ 
		
		IndexWriter writer = LuceneUtil.getIndexWriter();
		try {
			writer.deleteAll();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if (writer != null) {
				try {
					writer.close();
				}catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		
	}



	@Override
	public void updateIndex(TChinaCity city){
		
		IndexWriter writer = null;
		try {
			writer = LuceneUtil.getIndexWriter();
			// 匹配后删除 和 添加新的
			Document document = new Document();
			document.add(new Field("id", city.getId()+"", Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("name", city.getName(),Store.YES,Index.ANALYZED));
			document.add(new Field("parentId", city.getParentId()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("sort", city.getSort()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("status", city.getStatus()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("createTime", city.getCreateTime()+"",Store.YES,Index.NOT_ANALYZED));
			document.add(new Field("updateTime", city.getUpdateTime()+"",Store.YES,Index.NOT_ANALYZED));
			writer.updateDocument(new Term("id", city.getId()+""), document); 
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}



	@Override
	public void deleteIndex(TChinaCity city){
		
		IndexWriter writer = LuceneUtil.getIndexWriter();
		Term term = new Term("id",city.getId()+"");
		
		try {
			writer.deleteDocuments(term);
		}catch (IOException e) {
			logger.error(e.getMessage());
		}finally {
			if (writer != null) {
				try {
					writer.close();
				}catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	

}
