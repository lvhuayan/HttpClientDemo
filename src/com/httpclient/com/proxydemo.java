package com.httpclient.com;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class proxydemo {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		CloseableHttpClient httpclient=createSSLClientDefault();
		HttpHost proxy=new HttpHost("127.0.0.1",8888,"http");
		RequestConfig config1=RequestConfig.custom().setProxy(proxy).build();
		RequestConfig config2=RequestConfig.custom().setRedirectsEnabled(false).build();
		HttpGet httpget=new HttpGet("http://www.360buy.com");
		httpget.setConfig(config1);
		httpget.setConfig(config2);
		//方法一
//		httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		//方法二数组
//		Header[] as=new BasicHeader[2];
//		as[0]=new BasicHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		as[1]=new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9");
//		httpget.setHeaders(as);
//		//方法三对象
//		Header head1=new BasicHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
//		Header head2=new BasicHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		httpget.setHeader(head1);
//		httpget.setHeader(head2);	
		
		//方法四  hashmap
		int i=0;
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		Header[] as=new BasicHeader[headers.size()];
		for(String str:headers.keySet()){
			as[i]=new BasicHeader(str,headers.get(str));
			i++;
		}
		httpget.setHeaders(as);
		
		CloseableHttpResponse response=httpclient.execute(httpget);
		HttpEntity entity=response.getEntity();
		String content=EntityUtils.toString(entity, "utf-8");
		System.out.println(content);
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



