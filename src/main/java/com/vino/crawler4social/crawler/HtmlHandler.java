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
	private static MessageNotice messageNotice=new MessageNotice();
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
				if (!dataPersistence.queryInDatabaseByContent(content)) {
					dataPersistence.saveInDatabase("zhihu",
							new Date().toString(), "vino", content, href);
					log.info(content);
				//	messageNotice.send("知乎更新了一条状态", content);
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

	// 微博主页地址:http://weibo.cn/u/2360059850?page=3&vt=4&PHPSESSID=
	/**
	 * 不能爬用来登录的账号的页面，因为url不对，返回的页面是不对的
	 * @param html
	 * @param lastContent
	 * @param saveDir
	 */
	public static void weiboHandler(String html, List<String> lastContent,
			String saveDir) {
		log.info("处理微博");
		if (html != null) {
			Document document = Jsoup.parse(html);

			Elements eles = document.select("div.c");// 微博内容

			Elements pagelist = document.select("div#pagelist>form>div");// 页数
			Elements timeAndDevice = document.select("span.ct");
			Elements nickname = document.select("div.ut>span.ctt");
			String content = null;
			DataPersistence dataPersistence = new DataPersistence();
			String pagetext = pagelist.get(0).text().toString();
			String timeAndDeviceText = null;// 微博发出时间和使用设备
			String totalPage = pagetext.substring(
					pagetext.lastIndexOf("/") + 1, pagetext.lastIndexOf("页"));
			String nicknameText = nickname.get(0).text().toString();
		
			// 处理原创
			for (int i = 0; i < eles.size() - 2; i++) {
				//清楚赞，评论等信息，因为该信息会一直在变导致数据库中content值会一直变化，错误更新
				content = eles.get(i).text().substring(0,eles.get(i).text().indexOf("赞["));
				timeAndDeviceText = timeAndDevice.get(i).text();
				if (!dataPersistence.queryInDatabaseByContent(content)) {

					dataPersistence.saveInDatabase("weibo",
							new Date().toString(), nicknameText, content, null);
					log.info("更新一条状态：" + content);
				//	messageNotice.send(nicknameText+"微博更新了一条状态", content);
				}
				try {
					DataPersistence.saveFile(saveDir + "log.txt", (new Date())
							+ content);
				} catch (IOException e) {

					e.printStackTrace();
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
				DataPersistence dataPersistence = new DataPersistence();
				if (!dataPersistence.queryInDatabaseByContent(content)) {
					dataPersistence.saveInDatabase("jianshu",
							new Date().toString(), nickname, content, href);
					log.info(nickname+"更新状态"+content);
				//	messageNotice.send(nickname+"简书更新了一条状态", content);
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

}
