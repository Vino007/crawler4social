package com.vino.crawler4social.crawler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Joker
 *
 */
public class WeiboCrawler {
	private static final Logger log = Logger.getLogger(WeiboCrawler.class);

	private CloseableHttpClient clients;
	private HttpGet httpGet;
	private String cookie;

	public WeiboCrawler() {
		try {
			cookie = WeiboLogin.getSinaCookie("540134090@qq.com", "Zhuoyue007");
		} catch (Exception e) {

			e.printStackTrace();
		}
	
		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		clients = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.build();

	}

	public String getHtml(String url) {
		String html = null;
		httpGet = new HttpGet(url);
		// Î±×°³Éä¯ÀÀÆ÷
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		httpGet.addHeader(new BasicHeader("Cookie", cookie));
		CloseableHttpResponse response = null;
		try {
			response = clients.execute(httpGet);

			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (entity != null)
					try {
						html = EntityUtils.toString(entity);

						EntityUtils.consume(entity);
					} catch (ParseException | IOException e) {

						e.printStackTrace();
					}
				else
					log.info("entityÎªnull");
			} else
				log.info("´íÎó´úÂë£º" + response.getStatusLine().getStatusCode());

		} catch (IOException e) {

			e.printStackTrace();
		} finally {

			try {
				response.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return html;
	}

	
}
