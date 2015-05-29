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
			log.info("处理知乎");
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

	public static void weiboHandler(String html, List<String> lastContent,
			String saveDir) {

		if (html != null) {
			Document document = Jsoup.parse(html);

			Elements eles = document.select("span.ctt");
			Elements cmts = document.select("span.cmt");// 转发
			String content = null;
			// 处理原创
			for (Element ele : eles) {
				content = ele.text();
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

			}
			// 处理转发
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

			}

		}
	}

	public static void jianshuHandler(String html, List<String> lastContent,
			String saveDir) {
		if (html != null) {
			Document document = Jsoup.parse(html);
			log.info("处理简书");
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
			// 处理原创
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
