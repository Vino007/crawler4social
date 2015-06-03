package com.vino.crawler4social.crawler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class WeiboCrawler {
	private static final Logger log = Logger.getLogger(WeiboCrawler.class);

	private CloseableHttpClient clients;
	private HttpGet httpGet;
	private String cookie;
	public WeiboCrawler() {
		 try {
			cookie=WeiboLogin.getSinaCookie("540134090@qq.com", "Zhuoyue007");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(cookie);
		 RequestConfig requestConfig=RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		 clients=HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		 
	}
	public  String getHtml() {
		String html=null;
		httpGet=new HttpGet("http://weibo.cn/2072525582/profile?vt=4&PHPSESSID=");
		httpGet.addHeader(new BasicHeader("Cookie", cookie));
		CloseableHttpResponse response=null;
		try {
			response = clients.execute(httpGet);
			
			HttpEntity entity=response.getEntity();
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			if(entity!=null)
				try {
					html=EntityUtils.toString(entity);
					log.info(html);
					EntityUtils.consume(entity);
				} catch (ParseException | IOException e) {
					
					e.printStackTrace();
				}
			else
				log.info("entity为null");
			}else
				log.info("错误代码："+response.getStatusLine().getStatusCode());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{

			try {
				response.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
						
		return html;
	}
	public static void main(String[] args) {
		WeiboCrawler crawler=null;
		try {
			crawler = new WeiboCrawler();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			log.info("开始获取html");
			crawler.getHtml();
			log.info("结束");
		
	}
}
