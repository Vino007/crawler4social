package com.vino.crawler4social.datapersistence;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

public class DataPersistence {
	private String url=null;
	private String username=null;
	private String password=null;
	 public DataPersistence(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("找不到驱动");
			
		}
		Properties prop=new Properties();
		InputStream in=DataPersistence.class.getResourceAsStream("/database.properties");
		
		try {
			prop.load(in);
			url=prop.getProperty("url");
			password=prop.getProperty("password");
			username=prop.getProperty("username");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
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
	
	public void saveInDatabase(String website,String time,String nickname,String content,String href){
		
		Connection con=null;
		PreparedStatement statement=null;
		String sql=null;
	
		try {
		
		   con=(Connection) DriverManager.getConnection(url, username, password);
		   sql="insert into social values(null,?,?,?,?,?)";
		   statement=(PreparedStatement) con.prepareStatement(sql);
		   statement.setString(1, website);
		   statement.setString(2, time);
		   statement.setString(3,nickname);
		   statement.setString(4, content);
		   statement.setString(5, href);
		   statement.execute();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally{
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			
		}
		
	}
	
	public boolean queryInDatabaseByContent(String content){
		String sql="select id from social where content=?";
		Connection con=null;
		PreparedStatement statement=null;
			
		try {
		
		   con=(Connection) DriverManager.getConnection(url, username, password);
		   
		   statement=(PreparedStatement) con.prepareStatement(sql);
		   statement.setString(1, content);
		   ResultSet result=statement.executeQuery();
		   if(result.next())
			   return true;
		   else 
			   return false;
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally{
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return true;
	}
}
