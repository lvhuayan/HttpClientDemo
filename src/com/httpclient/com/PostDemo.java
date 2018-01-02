package com.httpclient.com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PostDemo {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		HttpEntity entity=null;
		String responseContent=null;
		String url="http://ztest.huasuhui.com/index.php?app_act=admin/login/admin_login";
		httpClient=HttpClients.createDefault();
		HttpPost HttpPost=new HttpPost(url);
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username","admin"));
		nameValuePairs.add(new BasicNameValuePair("password","Hsh@Zt201709."));
		HttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
		response=httpClient.execute(HttpPost);
		entity=response.getEntity();
		responseContent=EntityUtils.toString(entity, "utf-8");
		System.out.println(responseContent);
		httpClient.close();
		
		

	}

}
