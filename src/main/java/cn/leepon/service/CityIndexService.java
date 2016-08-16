package cn.leepon.service;

import java.util.List;

import cn.leepon.po.TChinaCity;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月15日 上午10:55:35   
 */
public interface CityIndexService {
	
	/**
	 * 
	 * @Title: buildIndex 
	 * @Description: TODO <建立索引>
	 * @param @param city 
	 * @return void  
	 * @throws
	 */
	public void buildIndex(TChinaCity city);
	
	/**
	 * 
	 * @Title: buildIndex 
	 * @Description: TODO <建立索引>
	 * @param @param list 
	 * @return void  
	 * @throws
	 */
	public void buildIndex(List<TChinaCity> list);
	
	/**
	 * 
	 * @Title: deleteAllIndex 
	 * @Description: TODO <删除全部索引>
	 * @param  
	 * @return void  
	 * @throws
	 */
	public void deleteAllIndex();
	
	/**
	 * 
	 * @Title: deleteIndex 
	 * @Description: TODO <删除索引>
	 * @param @param city 
	 * @return void  
	 * @throws
	 */
	public void deleteIndex(TChinaCity city);
	
		
	/**
	 * 
	 * @Title: updateIndex 
	 * @Description: TODO <更新索引>
	 * @param @param city 
	 * @return void  
	 * @throws
	 */
	public void updateIndex(TChinaCity city);
	
}
