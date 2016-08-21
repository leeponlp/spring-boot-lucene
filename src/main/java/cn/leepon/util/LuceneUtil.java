package cn.leepon.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 
 * @ClassName: LuceneUtil
 * @Description: TODO
 * @author leepon
 * @date 2016年8月16日 上午10:35:25
 *
 */
public class LuceneUtil {

	private static Logger logger = Logger.getLogger(LuceneUtil.class);

	private static Directory directory;

	private static Version version;

	private static Analyzer analyzer;

	static {
		try {
			directory = FSDirectory.open(new File("index"));
			version = Version.LUCENE_36;
			analyzer = new IKAnalyzer();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 索引目录
	public static Directory getDirectory() {
		Directory fsdir = null;
		try {
			fsdir = FSDirectory.open(new File("index"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fsdir;
	}

	// 分词器
	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	// 提供获取IndexWriterConfig对象
	public static IndexWriterConfig getIndexWriterConfig() {
		IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		return iwc;
	}
	
	
	//提供获取IndexReader对象
	public static IndexReader getIndexReader(){
		IndexReader reader = null;
		try {
			reader = IndexReader.open(directory);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}

	// 提供获取IndexWriter对象
	public static IndexWriter getIndexWriter() {
		IndexWriter indexWriter = null;
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(directory, iwc);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return indexWriter;
	}

	// 获得查询IndexSeacher对象
	public static IndexSearcher getIndexSearcher(){
		return new IndexSearcher(getIndexReader());
	}

	/**
	 * 
	 * @Title: buildIndex 
	 * @Description: TODO <构建单文档索引> 
	 * @param doc 
	 * @return void 
	 * @throws
	 */
	public static void buildIndex(Document doc) {
		IndexWriter writer = getIndexWriter();
		try {
			writer.addDocument(doc);
			commit(writer);
		} catch (Exception e) {
			rollback(writer);
			e.printStackTrace();
		} finally {
			close(writer);
		}

	}

	/**
	 * 
	 * @Title: rebuildAllIndex 
	 * @Description: TODO <构建全部文档索引> 
	 * @param doclist
	 * @return void 
	 * @throws
	 */
	public static void rebuildAllIndex(List<Document> doclist) {
		IndexWriter writer = null;
		try {
			writer = getIndexWriter();
			for (Document document : doclist) {
				writer.addDocument(document);
			}
			commit(writer);
		} catch (Exception e) {
			rollback(writer);
			e.printStackTrace();
		} finally {
			close(writer);
		}
	}

	/**
	 * 
	 * @Title: deleteAllIndex 
	 * @Description: TODO <删除全部索引> 
	 * @param void
	 * @throws
	 */
	public static void deleteAllIndex() {

		IndexWriter writer = getIndexWriter();
		try {
			writer.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(writer);
		}

	}

	/**
	 * 
	 * @Title: deleteIndex 
	 * @Description: TODO <删除索引> 
	 * @param terms 
	 * @return void
	 * @throws
	 */
	public static void deleteIndex(Term... terms) {

		IndexWriter writer = null;
		try {
			writer = getIndexWriter();
			writer.deleteDocuments(terms);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(writer);
		}

	}

	/**
	 * 
	 * @Title: updateIndex 
	 * @Description: TODO <更新文档索引> 
	 * @param term
	 * @param doc 
	 * @return void 
	 * @throws
	 */
	public static void updateIndex(Term term, Document doc) {
		IndexWriter writer = null;
		try {
			writer = getIndexWriter();
			writer.updateDocument(term, doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(writer);
		}
	}
	
	/**
	 * 
	 * @Title: searchTotalRecord 
	 * @Description: TODO <索引命中数>
	 * @param query
	 * @return int  
	 * @throws
	 */
	public static int searchTotalRecord(Query query) {
		ScoreDoc[] docs = null;
		try {
			IndexSearcher indexSearcher = getIndexSearcher();
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
			if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
				return 0;
			}
			docs = topDocs.scoreDocs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs.length;
	}
	
	
	/**
	 * 
	 * @Title: searchRows 
	 * @Description: TODO <索引文档查询>
	 * @param query
	 * @return List<Document>  
	 * @throws
	 */
	public static List<Document> searchRows(Query query){
		List<Document> doclist = new ArrayList<>();
		try {
			IndexSearcher indexSearcher = getIndexSearcher();
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("搜索结果 " + topDocs.totalHits + "条");
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = indexSearcher.doc(doc);
				doclist.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}
	
	/**
	 * 
	 * @Title: searchRows 
	 * @Description: TODO <索引文档查询并排序>
	 * @param query
	 * @param sort
	 * @return List<Document>  
	 * @throws
	 */
	public static List<Document> searchRows(Query query,Sort sort){
		List<Document> doclist = new ArrayList<>();
		try {
			IndexSearcher indexSearcher = getIndexSearcher();
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE,sort);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("搜索结果 " + topDocs.totalHits + "条");
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = indexSearcher.doc(doc);
				doclist.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}

	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: TODO <分页查询> 
	 * @param query 
	 * @param start
	 * @param limit 
	 * @return List<Document> 
	 * @throws
	 */
	public static List<Document> pageQuery(Query query, int start, int limit) {
		List<Document> doclist = new ArrayList<>();
		if (start <= 0)start = 1;
		try {
			IndexSearcher indexSearcher = getIndexSearcher();
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("搜索结果 " + topDocs.totalHits + "条");

			// 查询起始记录位置
			int begin = (start - 1) * limit;
			if (begin > topDocs.totalHits) {
				throw new Exception("pageQueryException:起始位置大于总记录数");
			}
			// 查询终止记录位置
			int end = Math.min(begin + limit, topDocs.totalHits);

			for (int i = start; i < end; i++) {
				int doc = scoreDocs[i-1].doc;
				Document document = indexSearcher.doc(doc);
				doclist.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}

	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: TODO <分页查询并排序>
	 * @param query
	 * @param start
	 * @param limit
	 * @param sort 
	 * @return List<Document>  
	 * @throws
	 */
	public static List<Document> pageQuery(Query query, int start, int limit, Sort sort) {
		List<Document> doclist = new ArrayList<>();
		if (start <= 0)start = 1;
		try {
			IndexSearcher indexSearcher = getIndexSearcher();
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE, sort);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			logger.info("搜索结果 " + topDocs.totalHits + "条");

			// 查询起始记录位置
			int begin = (start - 1) * limit;
			if (begin > topDocs.totalHits) {
				throw new Exception("pageQueryException:起始位置大于总记录数");
			}
			// 查询终止记录位置
			int end = Math.min(begin + limit, topDocs.totalHits);

			for (int i = start; i < end; i++) {
				int doc = scoreDocs[i-1].doc;
				Document document = indexSearcher.doc(doc);
				doclist.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}

	// 提交IndexWriter
	public static void commit(IndexWriter writer) {

		try {
			if (writer != null) {
				writer.commit();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	// 回滚IndexWriter
	public static void rollback(IndexWriter writer) {

		try {
			if (writer != null) {
				writer.rollback();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	// 关闭IndexWriter
	public static void close(IndexWriter writer) {

		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
