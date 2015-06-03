package com.vino.crawler4social.crawler;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vino.crawler4social.datapersistence.DataPersistence;

public class HtmlHandler {
	private static final Logger log = Logger.getLogger(HtmlHandler.class);

	public static void zhihuHandler(String html, List<String> lastContent,
			String saveDir) {
		if (html != null) {
			Document document = Jsoup.parse(html);
			log.info("����֪��");
			Elements eles = document
					.select("div.zm-profile-section-main.zm-profile-section-activity-main.zm-profile-activity-page-item-main");

			String content = null;
			String href = null;
			for (Element ele : eles) {
				content = ele.text();
				href = "www.zhihu.com" + ele.child(1).attr("href");

				DataPersistence dataPersistence = new DataPersistence();
				if (!dataPersistence
						.queryInDatabase("select id from social where content="
								+ "'" + content + "'")) {
					dataPersistence.saveInDatabase("zhihu",
							new Date().toString(), "vino", content, href);
					log.info(content);
					try {
						DataPersistence.saveFile(saveDir + "log.txt",
								(new Date()) + content);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

	}
	//΢����ҳ��ַ:http://weibo.cn/u/2360059850?page=3&vt=4&PHPSESSID=
	public static void weiboHandler(String html, List<String> lastContent,
			String saveDir) {
		log.info("����΢��");
		if (html != null) {
			Document document = Jsoup.parse(html);

			Elements eles = document.select("div.c");//ԭ��"span.ctt"
		//	Elements cmts = document.select("span.cmt");// ת��
			Elements pagelist=document.select("div#pagelist>form>div");
			Elements timeAndDevice=document.select("span.ct");
			String content = null;
			String href = null;
		
			DataPersistence dataPersistence = new DataPersistence();
			String pagetext=pagelist.get(0).text().toString();
			
			String timeAndDeviceText=null;//΢������ʱ���ʹ���豸
			String totalPage=pagetext.substring(pagetext.lastIndexOf("/")+1, pagetext.lastIndexOf("ҳ"));			
			log.info(totalPage);
			// ����ԭ��
			for (int i=0;i<eles.size()-2;i++) {
				content = eles.get(i).text();
				timeAndDeviceText=timeAndDevice.get(i).text();
				//href = "www.weibo.cn" + ele.child(1).attr("href");
			//	dataPersistence.saveInDatabase("weibo",
			//			new Date().toString(), "vino", content, href);
				log.info(content);
				log.info(timeAndDeviceText);
					try {
						DataPersistence.saveFile(saveDir + "log.txt",
								(new Date()) + content);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			
	/*		// ����ת��
			for (Element cmt : cmts) {
				content = cmt.text();
				if (!lastContent.contains(content)) {
					lastContent.add(content);
					log.info(content + "     ");
					try {
						DataPersistence.saveFile(saveDir + "log.txt",
								(new Date()) + content);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}*/

		}
	}

	public static void jianshuHandler(String html, List<String> lastContent,
			String saveDir) {
		if (html != null) {
			Document document = Jsoup.parse(html);
			log.info("�������");
			Elements eles = document.select("ul.timeline-content>li");

			String content = null;
			String href = null;
			String nickname = null;
			for (Element ele : eles) {
				content = ele.text();
				if (ele.child(1).child(1).children().size() != 0)
					href = "www.jianshu.com"
							+ ele.child(1).child(1).child(0).attr("href");
				nickname = ele.child(1).child(0).text();
				log.info(nickname + href);
				DataPersistence dataPersistence = new DataPersistence();
				if (!dataPersistence
						.queryInDatabase("select id from social where content="
								+ "'" + content + "'")) {
					dataPersistence.saveInDatabase("jianshu",
							new Date().toString(), nickname, content, href);
					log.info(content + "     ");
					try {
						DataPersistence.saveFile(saveDir + "log.txt",
								(new Date()) + content);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}
		}
	}

	public static void doubanHandler(String html, List<String> lastContent,
			String saveDir) {
		if (html != null) {
			Document document = Jsoup.parse(html);
			System.out.println(html);
			Elements eles = document.select("ul.timeline-content>li");

			String content = null;
			// ����ԭ��
			/*
			 * for (Element ele : eles) { content = ele.text(); if
			 * (!lastContent.contains(content)) { lastContent.add(content);
			 * log.info(content + "     "); try {
			 * DataPersistence.saveFile(saveDir + "log.txt", (new Date()) +
			 * content); } catch (IOException e) {
			 * 
			 * e.printStackTrace(); } }
			 * 
			 * }
			 */
		}
	}
}
