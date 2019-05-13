package qtgl.utils;



import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Response<T>  {
	
	public static final int ERR_SUCCEED = 0;
	public static final int ERR_UNKNOWN = -1;
	public static final int ERR_EXCEPTION = -2;
	public static final int ERR_UNSUPPORTED = -3;
	public static final int ERR_BADPARAMETERS = 10001;
	public static final int ERR_ACCESSDENIED = 10002;
	public static final int ERR_OVERTIME = 9999;
	public static final int WARN_EXISTUSER = 16002;
	
	
	@SuppressWarnings("rawtypes")
	public final static Response UNSUPPORTED = new Response<Object>(ERR_UNSUPPORTED, "not supported");
	
	@SuppressWarnings("rawtypes")
	public final static Response BADRPARAMETERS = new Response<Object>(ERR_BADPARAMETERS, "bad parameters");
	
	@SuppressWarnings("rawtypes")
	public final static Response ACCESSDENIED = new Response<Object>(ERR_ACCESSDENIED, "authentication failed");

	@Expose
	private boolean success;
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Expose
	private int errno;
	
	@Expose
	private String error;

	@Expose
	private int total;
	
	@Expose
	private int pageNumber;
	
	@Expose
	private int totalPage;
	
	@Expose
	private String nextToken;
	
	@Expose
	protected List<T> entities;
	
	
	private transient Type type;
	
	public Response() {
		this(ERR_SUCCEED, "success");
	}
	
	public Response(Type type) {
		this();
		this.setType(type);
	}

	public Response(int errno, String error) {
		this.setErrno(errno);
		this.setError(error);
	}

	public boolean isSuccess() {
		return success;
	}
	
	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
		this.success = errno == ERR_SUCCEED;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getNextToken() {
		return nextToken;
	}

	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	
	

}

