package cn.leepon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import au.com.bytecode.opencsv.CSVReader;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年8月22日 上午10:56:54   
 */
public class CsvUtil {
	
	/**
     * @param file
     * @param rowIndex 从第几行读取数据
     * @return
     * @throws Exception
     */
    public static List<List<String>> readCsv(String file, int rowIndex,String charsetName) {
    	CSVReader csvReader = null;
    	List<List<String>> retList = null;
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),charsetName);
            csvReader = new CSVReader(reader);
            List<String[]> list = csvReader.readAll();
            if (CollectionUtils.isNotEmpty(list)) {
            	retList = new ArrayList<List<String>>();
                //每一行为一个String数组,如果没有指定从第几行读取数据,则默认第一行为标题栏,从第二行开始
                if (rowIndex == 0) rowIndex = 1;
                for (int i = rowIndex; i < list.size(); i++) {
                    List<String> rowList = Arrays.asList(list.get(i));
                    retList.add(rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return retList;  
       }
 
    /**
     * @param file 
     * @param rowIndex 从第几行读取数据
     * @return
     */
    public static List<List<String>> readCsv(File file, int rowIndex,String charsetName) {
    	CSVReader csvReader = null;
    	List<List<String>> retList = null;
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),charsetName);
            csvReader = new CSVReader(reader);
            List<String[]> list = csvReader.readAll();
            if (list != null && list.size() != 0) {
                retList = new ArrayList<List<String>>();
                //每一行为一个String数组,如果没有指定从第几行读取数据,则默认第一行为标题栏,从第二行开始
                if (rowIndex == 0)rowIndex = 1;
                for (int i = rowIndex; i < list.size(); i++) {
                    List<String> rowList = Arrays.asList(list.get(i));
                    retList.add(rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return retList; 
    }
    

}
