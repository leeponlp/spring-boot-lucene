package cn.leepon.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.leepon.po.TChinaCity;
import cn.leepon.service.CityService;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月15日 上午10:40:55   
 */

@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityService cityService;
	
	@RequestMapping("/list")
	public List<TChinaCity> listCity() throws Exception{
		
		return cityService.listCity(); 
	}
	
	@RequestMapping("/list/{name}")
	public List<TChinaCity> queryCityByName(@PathVariable String name) throws Exception{
		
		return cityService.queryCityByName(name); 
	}

	

}
