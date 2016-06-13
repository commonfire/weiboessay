package edu.bupt.util.https;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	
	public static void main(String[] args) throws IOException {
		String url = "http://api.ltp-cloud.com/analysis/?api_key=n5Y5v584kMmPSXMaLyYABlxpatwMRUFEaAGB2cgX&text=纵使我们相距这么远，我依然我爱你&pattern=sdp&format=json";
/*		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("api_key", "n5Y5v584kMmPSXMaLyYABlxpatwMRUFEaAGB2cgX"));
		nvps.add(new BasicNameValuePair("text", "我是中国人。"));
		nvps.add(new BasicNameValuePair("pattern", "sdp"));
		nvps.add(new BasicNameValuePair("format", "json"));
		JSONObject jsonObject = httpPost(url, nvps, true);*/
		httpGet(url);
	}
	
	/**
	 * 发送http post请求
	 * @param url           请求的url地址
	 * @param nvps          post请求的参数
	 * @param needResponse  是否需要返回响应结果
	 * @return              返回响应的JSON数据
	 * @throws IOException 
	 */
	public static JSONObject httpPost(String url, List<NameValuePair> nvps, boolean needResponse) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		JSONObject jsonResult = null;
		if (nvps != null) httpPost.setEntity(new UrlEncodedFormEntity(nvps)); //设置post参数
		
		try {
			response = httpClient.execute(httpPost);
			if (200 == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				String str = EntityUtils.toString(entity,"utf-8");
				if (!needResponse) return null;
				//jsonResult = JSONObject.fromObject(str);
				System.out.println(str);
				EntityUtils.consume(entity);
			}
			else System.out.println(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			response.close();
		}
		return jsonResult;
	}
	
	/**
	 * 发送http get请求
	 * @param url     		请求的url地址
	 * @return              返回响应的String类型数据
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpGet(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		String result = "";
		try {
			response = httpClient.execute(httpGet);
			if (200 == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
				//jsonResult = JSONObject.fromObject(str);
				EntityUtils.consume(entity);
			}
			else System.out.println(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			response.close();
		}
		return result;
	}
	
}
