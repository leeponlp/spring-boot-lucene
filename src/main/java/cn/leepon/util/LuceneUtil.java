package cn.leepon.util;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
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
	
	private static IndexWriterConfig indexWriterConfig;
	
	private static IndexWriter indexWriter;
	
	
	
	static{
		try {
			directory = FSDirectory.open(new File("index"));
			version = Version.LUCENE_36;
			analyzer = new IKAnalyzer();
			indexWriterConfig = new IndexWriterConfig(version,analyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			indexWriter = new IndexWriter(directory,indexWriterConfig);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
			Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				super.run();
				try {
					indexWriter.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				} 
			}
		});
	}
		
	//索引目录
	public static Directory getDirectory(){
		return directory;
	}
	
	//分词器
	public static Analyzer getAnalyzer(){
		return analyzer;
	}
		
	//提供获取IndexWriterConfig对象
	public static IndexWriterConfig getIndexWriterConfig(){
		return indexWriterConfig;
	}
	
	// 提供获取IndexWriter对象
	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}

	// 获得查询IndexSeacher对象
	public static IndexSearcher getIndexSearcher() throws Exception {
		return new IndexSearcher(IndexReader.open(directory)); 
	}


}
