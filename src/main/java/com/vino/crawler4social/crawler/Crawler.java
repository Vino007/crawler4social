package com.vino.crawler4social.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.vino.crawler4social.network.DataObtain;

/**
 * 
 * @author Joker
 *
 */

public class Crawler {

	private static final Logger log = Logger.getLogger(Crawler.class);

	public static void main(String[] args) {

		String saveDir = null;// �ļ�����·��
		String html = null;// ��Ӧ��html
		ArrayList<String> lastContent = new ArrayList<String>();
		ArrayList<String> urls = new ArrayList<String>();

		Properties prop = new Properties();
		InputStream in = Crawler.class
				.getResourceAsStream("/crawler.properties");
		try {
			prop.load(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
				WeiboCrawler crawler = new WeiboCrawler();
				html = crawler.getHtml(urls.get((urlIndex)));
				try{
				HtmlHandler.weiboHandler(html, lastContent, saveDir);
				}catch(Exception e){
					e.printStackTrace();
					log.error("΢�������޷�ʹ��");
				}				
				break;
			default:
				break;
			}
			urlIndex++;
			urlIndex = urlIndex % urls.size();
			// ������Ϣ���������Ƶ����ѯ
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}
