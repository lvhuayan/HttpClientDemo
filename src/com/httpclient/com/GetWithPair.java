package com.httpclient.com;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class GetWithPair {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		CloseableHttpClient httpclient=createSSLClientDefault();
		//设置代理
		HttpHost proxy=new HttpHost("127.0.0.1",8888,"http");
		RequestConfig config=RequestConfig.custom().setProxy(proxy).build();
		//带参数的get请求
		List<NameValuePair> nameValuePair=new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("q","朝花夕拾"));
		nameValuePair.add(new BasicNameValuePair("count","5"));
		String str=EntityUtils.toString(new UrlEncodedFormEntity(nameValuePair,Consts.UTF_8));		
		HttpGet httpget=new HttpGet("https://api.douban.com/v2/book/search"+"?"+str);
		httpget.setConfig(config);
		//设置请求头
		httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");	
		CloseableHttpResponse response=httpclient.execute(httpget);
		//获取http响应状态码
		int code=response.getStatusLine().getStatusCode();
		System.out.println(code);
		HttpEntity entity=response.getEntity();
		String ResponseContent=EntityUtils.toString(entity, "utf-8");	
		//System.out.println(ResponseContent);
		
		
		//JSON解析
		JSONObject jo=new JSONObject(ResponseContent);
		//System.out.println(jo);
		String jo1=jo.getJSONArray("books").getJSONObject(0).getJSONObject("images").getString("large");
		System.out.println(jo1);
		httpclient.close();
	}
	
    /** 
     * HttpClient连接SSL 
     */
	//信任所有
	public static CloseableHttpClient createSSLClientDefault(){
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy(){
				public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException{
					return true;	
				}			
			}).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();

		
	}


}



