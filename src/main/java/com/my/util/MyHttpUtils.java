package com.my.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class MyHttpUtils {

	/**
	 * get请求
	 * 
	 * @param url
	 * @param defaultCharset
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public String httpGet(String url, String defaultCharset) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		HttpClient httpClient = HttpClients.custom().build();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		get.setConfig(requestConfig);
		HttpResponse response = httpClient.execute(get);
		return EntityUtils.toString(response.getEntity(), defaultCharset);
	}

	public String httpGet(String url) throws ClientProtocolException, IOException {
		return httpGet(url, "GBK");
	}

	public String httpPost(String url, HttpEntity entity) throws ClientProtocolException, IOException {

		return httpPost(url, entity, null);

	}

	public String httpPostJson(String url, HttpEntity entity) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		HttpClient httpClient = HttpClients.custom().build();
		post.setHeader("Content-Type", "application/json");

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		post.setConfig(requestConfig);
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);
		String result = null;
		result = EntityUtils.toString(response.getEntity(), "utf-8");
		return result;

	}

	public String httpPost(String url, HttpEntity entity, String returnCharset) throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		HttpClient httpClient = HttpClients.custom().build();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		post.setConfig(requestConfig);
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);
		String result = null;
		if (returnCharset == null) {
			result = EntityUtils.toString(response.getEntity());
		} else {
			result = EntityUtils.toString(response.getEntity(), returnCharset);
		}
		return result;
	}

	/**
	 * 将发送过来的数据以输入的编码的方式发送到请求端
	 * 
	 * @param url
	 *            请求的地址
	 * @param reqObj
	 *            发送请求的数据
	 * @param inputCharSet
	 *            发送数据的编码方式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public String httpPostJsonObj(String url, JSONObject reqObj, String inputCharSet) throws ClientProtocolException,
			IOException {

		HttpEntity entity = null;
		if (inputCharSet != null) {
			entity = new StringEntity(reqObj.toJSONString(), inputCharSet);
		} else {
			entity = new StringEntity(reqObj.toJSONString());
		}
		HttpPost post = new HttpPost(url);
		HttpClient httpClient = HttpClients.custom().build();
		post.setHeader("Content-Type", "application/json");

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		post.setConfig(requestConfig);
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);
		String result = null;
		result = EntityUtils.toString(response.getEntity(), "utf-8");
		return result;

	}

}
