package cn.leepon.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 写出txt文本
 * @author leepon
 *
 */
public class WriteUtil {
	

	public static <T> void WriteTxt(String fileName, List<T> list) {
		File file = new File(fileName);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			for (T t : list) {
				bw.write(t.toString());
				bw.newLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
//	public static void main(String[] args) {
//		List<TUser> list = new ArrayList<TUser>();
//		TUser user1 = new TUser();
//		user1.setIautoid(1);
//		user1.setIbroker((byte) 0);
//		user1.setSheadportraitext("aaaaa");
//		TUser user2 = new TUser();
//		user2.setIautoid(2);
//		user2.setIbroker((byte) 0);
//		user2.setSheadportraitext("bbbbb");
//		TUser user3 = new TUser();
//		user3.setIautoid(3);
//		user3.setIbroker((byte) 0);
//		user3.setSheadportraitext("ccccc");
//		TUser user4 = new TUser();
//		user4.setIautoid(4);
//		user4.setIbroker((byte) 0);
//		user4.setSheadportraitext("ddddd");
//		TUser user5 = new TUser();
//		user5.setIautoid(5);
//		user5.setIbroker((byte) 0);
//		user5.setSheadportraitext("eeeee");
//		list.add(user1);
//		list.add(user2);
//		list.add(user3);
//		list.add(user4);
//		list.add(user5);
//		WirteUtil.WriteTxt("2.txt", list); 
//		}	

}
