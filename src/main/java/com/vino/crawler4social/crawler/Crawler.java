package com.vino.crawler4social.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vino.crawler4social.datapersistence.DataPersistence;
import com.vino.crawler4social.network.DataObtain;

/**
 * 
 * @author Joker
 *
 */
/*
 * Elements childs=ele.children(); if(childs.select(".question_link").size()!=0)
 * content=childs.select(".question_link").text(); else
 * if(childs.select(".topic_link").size()!=0){
 * content=childs.select(".topic_link").text(); }else
 * if(childs.select(".column_link").size()!=0){
 * content=childs.select(".column_link").text(); }
 */
public class Crawler {

	private static final Logger log = Logger.getLogger(Crawler.class);

	public static void main(String[] args) throws IOException,
			InstantiationException, IllegalAccessException {

	
		String saveDir = null;// 文件保存路径
		String html = null;// 响应的html
		ArrayList<String> lastContent = new ArrayList<String>();
		ArrayList<String> urls = new ArrayList<String>();
	
		Properties prop = new Properties();
		InputStream in = Crawler.class
				.getResourceAsStream("/crawler.properties");
		prop.load(in);
		in.close();
	
		urls.add(prop.getProperty("zhihuUrl"));
		urls.add(prop.getProperty("jianshuUrl"));
	
		saveDir = prop.getProperty("saveDir");

		int urlIndex = 0;
		while (true) {
			 html = DataObtain
						.getHTMLByGET(urls.get((urlIndex) % urls.size()));
			switch(urlIndex%urls.size()){
			case 0:HtmlHandler.zhihuHandler(html, lastContent, saveDir);break;
			case 1:	HtmlHandler.jianshuHandler(html, lastContent, saveDir);;break;
			default:break;
			}
			urlIndex++;
			//爬虫休息，避免过于频繁查询
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
