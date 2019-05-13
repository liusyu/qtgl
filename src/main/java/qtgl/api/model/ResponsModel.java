package qtgl.api.model;

import java.util.List;

public class ResponsModel<T> {

	//成功：200
	//apiType参数不能为空：1010
	//apiType参数内容错误：1011
	
	//Action参数不能为空：1020
	//Action参数不能为空：1021
	
	//发起成功，entities数据为空！：1030
	
	//entities数据为空 1040
	
	private int errno;
	
	private String error;
	
	private String message;

	protected List<T> entities;
	
	private Long TotalCount;
	
	
	
	public Long getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(Long totalCount) {
		TotalCount = totalCount;
	}

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}
 
}
