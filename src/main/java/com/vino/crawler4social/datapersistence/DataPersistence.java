package com.vino.crawler4social.datapersistence;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DataPersistence {
	public static void saveFile(String path, String content) throws IOException {
		File dir=new File(path.substring(0,path.lastIndexOf("\\")));
		
		File file = new File(path);
		/** 
		 * if(file.isDirectory) file.mkdir();
		 * 
		 * String fileName = url.substring(url.lastIndexOf("/"));  
           String filePath = PIC_DIR + "/" + fileName;  
		 */
		if(!dir.exists()){
			dir.mkdirs();
		System.out.println("createNewDir"+dir.getAbsolutePath());
		}
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("createNewFile"+ file.getName());
		}
		FileOutputStream fos = new FileOutputStream(file,true);//true保证每次输入的时候是追加而不是覆盖
		PrintWriter bw = new PrintWriter(fos,true);

		bw.println(content);;
		bw.close();
	}
	
	public static void downloadImage(String path, String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			byte[] byteArray = EntityUtils.toByteArray(response.getEntity());
			response.close();
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();

			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(file));

			if (byteArray.length > 0)
				bout.write(byteArray);

			bout.flush();
			bout.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
