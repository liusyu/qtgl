package qtgl.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpConnection {
	
	public static JSONObject doGet(String requestURL,Map<String, String> parameterMap) {
		List<NameValuePair> pairs = new ArrayList<>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}

		String query = "";
		if (pairs != null) {
			query = URLEncodedUtils.format(pairs, Charsets.UTF_8.name());
		}
		requestURL += "?" + query;

		// System.out.println(requestURL);

		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpGet httpGet = new HttpGet(requestURL);
		StringBuffer buffer = new StringBuffer();
		try {
			HttpResponse response = client.execute(httpGet);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Charsets.UTF_8));
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.fromObject(buffer.toString());
	}

	public static JSONObject doPut(String requestURL,Map<String, String> parameterMap) {

		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpPut httpPut = new HttpPut(requestURL);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}
		StringBuffer buffer = new StringBuffer();
		try {
			httpPut.setEntity(new UrlEncodedFormEntity(pairs, Charsets.UTF_8));
			HttpResponse response = client.execute(httpPut);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Charsets.UTF_8));
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.fromObject(buffer.toString());
	}

	public static JSONObject doPost(String requestURL,Map<String, String> parameterMap) {

		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpPost httpPost = new HttpPost(requestURL);

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}
		StringBuffer buffer = new StringBuffer();
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, Charsets.UTF_8));
			HttpResponse response = client.execute(httpPost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Charsets.UTF_8));
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.fromObject(buffer.toString());
	}
	
	
	/**
	 * 上传文件到file服务器
	 * @param requestURL  服务器地址
	 * @param localFilePath 本地文件地址
	 * @param parameterMap 其他参数
	 * @return
	 */
	public static String uploadFile(String requestURL, String localFilePath)
	{
		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpPut httpPut = new HttpPut(requestURL);
		StringBuffer buffer = new StringBuffer();
		try { 
			FileBody fileBody = new FileBody(new File(localFilePath)); 
			MultipartEntityBuilder create = MultipartEntityBuilder.create();
			create.setCharset(Charset.forName("utf-8"));  
			create.setMode(HttpMultipartMode.RFC6532);//解决中文乱码问题
			create.addPart("file", fileBody);
			HttpEntity build = create.build();
			httpPut.setEntity(build);
			HttpResponse response = client.execute(httpPut);
			buffer.append(EntityUtils.toString(response.getEntity(),"utf-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buffer.toString();
	}
	/**
	 * 上传文件 （使用文件流）
	 * @param requestURL 服务器地址
	 * @param output 文件流程
	 * @param filename 文件名称
	 * @return
	 */
	public static String uploadFile(String requestURL, ByteArrayOutputStream output,String filename)
	{
		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpPut httpPut = new HttpPut(requestURL);
		StringBuffer buffer = new StringBuffer();
		try { 
			
			MultipartEntityBuilder create = MultipartEntityBuilder.create();
			create.setCharset(Charset.forName("utf-8"));
			create.setMode(HttpMultipartMode.RFC6532);//解决中文乱码问题
			create.addBinaryBody("file", output.toByteArray(), ContentType.DEFAULT_BINARY,filename);
			HttpEntity build = create.build();
			httpPut.setEntity(build);
			HttpResponse response = client.execute(httpPut);
			buffer.append(EntityUtils.toString(response.getEntity(),"utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buffer.toString();
	}
	
	/**
	 * 下载文件到本地
	 * @param requestURL  导出API地址
	 * @param filePath   保存文件路径
	 * @param parameterMap 参数
	 */
	public static void httpDownloadFile(String requestURL,String filePath,Map<String, String> parameterMap)
	{
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}
		
		String query = "";
		if (pairs != null) {
			query = URLEncodedUtils.format(pairs, Charsets.UTF_8.name());
		}
		requestURL += "?" + query;
		
		
		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpGet httpGet = new HttpGet(requestURL);
		try {
			
			HttpResponse response = client.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			InputStream is = httpEntity.getContent();
			// 根据InputStream 下载文件
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int r = 0;
			while ((r = is.read(buffer)) > 0) {
				output.write(buffer, 0, r);
			}
			FileOutputStream fos = new FileOutputStream(filePath);
			output.writeTo(fos);
			output.flush();
			output.close();
			fos.close();
			EntityUtils.consume(httpEntity);
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static ByteArrayOutputStream httpDownloadFile(String requestURL,Map<String, String> parameterMap)
	{
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}
		
		String query = "";
		if (pairs != null) {
			query = URLEncodedUtils.format(pairs, Charsets.UTF_8.name());
		}
		requestURL += "?" + query;
		
		
		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpGet httpGet = new HttpGet(requestURL);
		try {
			
			HttpResponse response = client.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			InputStream is = httpEntity.getContent();
			// 根据InputStream 下载文件
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int r = 0;
			while ((r = is.read(buffer)) > 0) {
				output.write(buffer, 0, r);
			}
			output.flush();
			output.close();
			EntityUtils.consume(httpEntity);
			return output;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JSONObject doPost_json(String requestURL, String jsonParam) {

		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpPost httpPost = new HttpPost(requestURL);

		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		StringBuffer buffer = new StringBuffer();
		try {
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Charsets.UTF_8));
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.fromObject(buffer.toString());
	}

	public static JSONObject doDelete(String requestURL,Map<String, String> parameterMap) {

		List<NameValuePair> pairs = new ArrayList<>();
		if (parameterMap != null) {
			String key = null;
			String value = null;
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				key = entry.getKey();
				if (StringUtils.isNotEmpty(key)) {
					value = entry.getValue();
				} else {
					value = "";
				}
				pairs.add(new BasicNameValuePair(key, value));
			}
		}

		String query = "";
		if (pairs != null) {
			query = URLEncodedUtils.format(pairs, Charsets.UTF_8.name());
		}
		requestURL += "?" + query;

		// System.out.println(requestURL);

		HttpClient client =  new DefaultHttpClient();
		if (requestURL.startsWith("https")) {
			enableSSL(client);
		}
		HttpDelete httpDelete = new HttpDelete(requestURL);
		StringBuffer buffer = new StringBuffer();
		try {
			HttpResponse response = client.execute(httpDelete);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Charsets.UTF_8));
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.fromObject(buffer.toString());

	}


	private static TrustManager truseAllManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}

		public void checkClientTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}
	};

	private static void enableSSL(HttpClient httpclient) {
		// 调用ssl
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
