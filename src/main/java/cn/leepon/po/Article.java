package cn.leepon.po;

/**
 * 
 * @ClassName: Article 
 * @Description: TODO
 * @author leepon
 * @date 2016年8月20日 下午10:16:05 
 *
 */
public class Article {
	
	private Integer id;
	
	private String title;
	
	private String content;
	
	private Integer status;

	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



}
