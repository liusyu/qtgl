package qtgl.api.model;

import com.google.gson.annotations.Expose;

public class FileVo {
	
	@Expose
	private String mime;
	@Expose
	private long size;
	@Expose
	private long createTime;
	@Expose
	private long lastModified;
//	@Expose
//	private String meta;
	@Expose
	private String sha1Hash;
	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String kind;
	@Expose
	private String uri;
	
	

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

//	public String getMeta() {
//		return meta;
//	}
//
//	public void setMeta(String meta) {
//		this.meta = meta;
//	}

	public String getSha1Hash() {
		return sha1Hash;
	}

	public void setSha1Hash(String sha1Hash) {
		this.sha1Hash = sha1Hash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}