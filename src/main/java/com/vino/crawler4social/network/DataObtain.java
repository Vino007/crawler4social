package com.vino.crawler4social.network;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DataObtain {
	public static String getHTMLByGET(String url) {
		
		CloseableHttpClient client = HttpClients.createDefault();	
		HttpGet httpGet = new HttpGet(url);
		//伪装成浏览器
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		//httpGet.addHeader("Cookies","_T_WM=85bd9caa7d7cac6a70d83c526eb3f7a7; "
	//			+ "SUB=_2A254YpjmDeTxGeRO7FAU8ivJwz6IHXVbrDiurDV6PUJbrdANLXTDkW1FkTb2ex3VQdOERcPd5h0v_2RdlA..; gsid_CTandWM=4ujud6991EBMl0QkoDOQH8H9P9o");
		CloseableHttpResponse response = null;
		String html = null;
		try {
			response = client.execute(httpGet);

			HttpEntity entity = response.getEntity();

			if (entity != null)
				html = EntityUtils.toString(entity);

			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return html;
	}
	/**
	 * 通过代理来获取html
	 * @param url
	 * @param hostname
	 * @param port
	 * @return
	 */
	public static String getHTMLByProxy(String url,String hostname,int port) {
		
		HttpHost proxy=new HttpHost(hostname, port);
		CloseableHttpClient client = HttpClients.custom().setProxy(proxy).build();
	
		HttpGet httpGet = new HttpGet(url);
		//伪装成浏览器
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		CloseableHttpResponse response = null;
		String html = null;
		try {
			response = client.execute(httpGet);

			HttpEntity entity = response.getEntity();

			if (entity != null)
				html = EntityUtils.toString(entity);

			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return html;
	}
	
public static String getHTMLByPOST(String url) {
		
		CloseableHttpClient client = HttpClients.createDefault();	
		
		HttpPost httpPost=new HttpPost(url);
		//伪装成浏览器
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		httpPost.addHeader("Cookies","_T_WM=85bd9caa7d7cac6a70d83c526eb3f7a7; "
				+ "SUB=_2A254YpjmDeTxGeRO7FAU8ivJwz6IHXVbrDiurDV6PUJbrdANLXTDkW1FkTb2ex3VQdOERcPd5h0v_2RdlA..; gsid_CTandWM=4ujud6991EBMl0QkoDOQH8H9P9o");
		CloseableHttpResponse response = null;
		String html = null;
		try {
			response = client.execute(httpPost);

			HttpEntity entity = response.getEntity();

			if (entity != null)
				html = EntityUtils.toString(entity);

			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return html;
	}
}
