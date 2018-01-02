package com.httpclient.com;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class ResponseCode {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		CloseableHttpClient httpclient=HttpClients.createDefault();
		//设置代理
		HttpHost proxy=new HttpHost("127.0.0.1",8888,"http");
		RequestConfig config=RequestConfig.custom().setProxy(proxy).build();
		HttpGet httpget=new HttpGet("http://www.qq.com");
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
		
		//接收响应头方法一
//		Header[] headers=response.getAllHeaders();
//		int i=0;
//		while(i<headers.length){
//			System.out.println(headers[i].getName()+":"+headers[i].getValue());
//			i++;
//		}
		
		
		//接收响应头方法二
		Header head01=response.getFirstHeader("Content-Type");
		Header head02=response.getLastHeader("Connection");
		System.out.println(head01);
		System.out.println(head02);

		
		
		httpclient.close();
		

	}

}



