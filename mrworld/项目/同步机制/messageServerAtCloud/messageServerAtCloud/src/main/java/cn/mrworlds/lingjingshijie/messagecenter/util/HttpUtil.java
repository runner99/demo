package cn.mrworlds.lingjingshijie.messagecenter.util;


import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtil {


	public static String sendPostForm(String url, Map<String,Object> map)  {
		if(!url.startsWith ("http://")&&!url.startsWith ("https:")){
			url = "http://" + url;
		}

		log.info("url is "+url);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse = null;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		ContentType strContent= ContentType.create("text/plain", Charset.forName("UTF-8"));

		for(Entry<String,Object> entry:map.entrySet()){
			multipartEntityBuilder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()),strContent);
			log.info(entry.getKey()+"　 is "+entry.getValue());
		}

		HttpEntity httpEntity = multipartEntityBuilder.build();
		httpPost.setEntity(httpEntity);
		String info = null;
		try{
			httpResponse = httpClient.execute(httpPost);
			HttpEntity responseEntity = httpResponse.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
			StringBuffer buffer = new StringBuffer();
			String str = "";
			while( (str = reader.readLine())!=null) {
				buffer.append(str);
			}

			log.info("response info is "+buffer.toString());
			info = buffer.toString();
			httpClient.close();
			if(httpResponse!=null){
				httpResponse.close();
			}

			log.info("response is "+ info);
		}catch(Exception e){
			log.info("one exception is find ,message is "+ e.getMessage());
		}

		return info;
	}

	public static String sendPostJSON(String url, String data) {
		String response = null;

		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				StringEntity stringentity = new StringEntity(data,
						ContentType.create("text/json", "UTF-8"));
				httppost.setEntity(stringentity);
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());

			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String sendGet(String api, Map<String, Object> map) throws Exception {
		StringBuilder sc = new StringBuilder();

		Iterator<Entry<String, Object>> it = map.entrySet().iterator();

		if (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			sc.append('?');
			sc.append(entry.getKey());
			sc.append('=');
			sc.append(entry.getValue());
			while (it.hasNext()) {
				entry = it.next();
				sc.append('?');
				sc.append(entry.getKey());
				sc.append('=');
				sc.append(entry.getValue());
			}
		}

		URL realUrl = new URL(api);
		HttpURLConnection uri = (HttpURLConnection) realUrl.openConnection();
		uri.setRequestMethod("GET");
		uri.setDoOutput(true);
		uri.setDoInput(true);
		uri.connect();

		InputStream in = uri.getInputStream();
		InputStreamReader isr = new InputStreamReader(in, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer response_sb = new StringBuffer();

		String result = "";

		String line = null;

		while ((line = br.readLine()) != null) {
			response_sb.append(line);
		}

		result = response_sb.toString();

		in.close();

		return result;
	}


	public static boolean checkRightResponseFromCloudServer(String res){

		return res!=null&&res.contains ("\"status\":\"0001\"");
	}

	public static boolean checkRightResponseFromPayMoudel(String res){
		return res!=null&&res.contains ("\"status\":1");
	}


	public static <T> T dataFromResponse(String res, Class<?> t){
		try {
			if (res == null) {
				return null;
			}
			JSONObject jsonObject = JSONObject.parseObject(res);

			if (jsonObject.getInteger("status").equals(200)) {
				JSONObject data = jsonObject.getJSONObject("data");
				return (T) data.toJavaObject(t);
			}
			else {
				String err = jsonObject.getString("info");
				throw new RuntimeException(err);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public static void main(String[] args){
		String res = "{\"statusCode\":null,\"data\":{\"id\":17,\"name\":\"room2\",\"password\":\"zhoujian\",\"creator\":\"122\",\"limits\":-1,\"mode\":1,\"purpose\":null,\"description\":null,\"applyTime\":null,\"state\":0,\"startTime\":\"2021-12-02 11:58:16\",\"endTime\":\"2021-12-05 11:58:16\",\"template\":null,\"sign\":\"20p0600h\",\"location\":\"/roomresource\\\\20p0600h\",\"attendtype\":null,\"owner\":\"122\",\"messageurl\":\"ws://192.168.1.118:20010/ws\",\"initialstatus\":null},\"info\":null,\"status\":200}";

		Room room = dataFromResponse(res,Room.class);

		System.out.println(room);

	}
}
