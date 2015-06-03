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
		urls.add(prop.getProperty("weiboUrl"));
		saveDir = prop.getProperty("saveDir");

		int urlIndex = 0;
		while (true) {
			html = DataObtain.getHTMLByGET(urls.get((urlIndex)));
			switch (urlIndex % urls.size()) {
			case 0:
				HtmlHandler.zhihuHandler(html, lastContent, saveDir);
				break;
			case 1:
				HtmlHandler.jianshuHandler(html, lastContent, saveDir);				
				break;		
			 case 2:
				WeiboCrawler crawler=new WeiboCrawler();
				html=crawler.getHtml(urls.get((urlIndex)));
				HtmlHandler.weiboHandler(html,lastContent,saveDir);
				break;			
			default:
				break;
			}
			urlIndex++;
			urlIndex = urlIndex % urls.size();
			// 爬虫休息，避免过于频繁查询
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}
