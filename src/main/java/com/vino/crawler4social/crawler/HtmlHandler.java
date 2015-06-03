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
			log.info("����֪��");
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
					messageNotice.send("֪��������һ��״̬", content);
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

	// ΢����ҳ��ַ:http://weibo.cn/u/2360059850?page=3&vt=4&PHPSESSID=
	/**
	 * ������������¼���˺ŵ�ҳ�棬��Ϊurl���ԣ����ص�ҳ���ǲ��Ե�
	 * @param html
	 * @param lastContent
	 * @param saveDir
	 */
	public static void weiboHandler(String html, List<String> lastContent,
			String saveDir) {
		log.info("����΢��");
		if (html != null) {
			Document document = Jsoup.parse(html);

			Elements eles = document.select("div.c");// ΢������

			Elements pagelist = document.select("div#pagelist>form>div");// ҳ��
			Elements timeAndDevice = document.select("span.ct");
			Elements nickname = document.select("div.ut>span.ctt");
			String content = null;
			DataPersistence dataPersistence = new DataPersistence();
			String pagetext=null;
			String totalPage=null;
			String timeAndDeviceText = null;// ΢������ʱ���ʹ���豸
			String nicknameText=null;
			if(pagelist.size()>0){
			pagetext = pagelist.get(0).text().toString();
			totalPage = pagetext.substring(
			pagetext.lastIndexOf("/") + 1, pagetext.lastIndexOf("ҳ"));
			}
			if(nickname.size()>0)
			nicknameText = nickname.get(0).text().toString();
		
			// ����ԭ��
			for (int i = 0; i < eles.size() - 2; i++) {
				//����ޣ����۵���Ϣ����Ϊ����Ϣ��һֱ�ڱ䵼�����ݿ���contentֵ��һֱ�仯���������
				content = eles.get(i).text().substring(0,eles.get(i).text().indexOf("��["));
				timeAndDeviceText = timeAndDevice.get(i).text();
				if (!dataPersistence.queryInDatabaseByContent(content)) {

					dataPersistence.saveInDatabase("weibo",
							new Date().toString(), nicknameText, content, null);
					log.info("����һ��״̬��" + content);
					messageNotice.send(nicknameText+"΢��������һ��״̬", content);
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
				DataPersistence dataPersistence = new DataPersistence();
				if (!dataPersistence.queryInDatabaseByContent(content)) {
					dataPersistence.saveInDatabase("jianshu",
							new Date().toString(), nickname, content, href);
					log.info(nickname+"����״̬"+content);
					messageNotice.send(nickname+"���������һ��״̬", content);
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
