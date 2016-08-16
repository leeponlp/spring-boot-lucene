package cn.leepon.service;

import java.util.List;

import cn.leepon.po.TChinaCity;


/**
 * This class is used for ...
 * @author leepon1990
 * @version
 *       1.0, 2016年4月18日 下午4:27:49
 */
public interface CityService {

	public List<TChinaCity> listCity(); 
	
	public List<TChinaCity> queryAllByLucene() throws Exception;

	public List<TChinaCity> queryCityByName(String name);  

}
