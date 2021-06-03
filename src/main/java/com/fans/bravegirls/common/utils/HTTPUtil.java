package com.fans.bravegirls.common.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;



public class HTTPUtil {
	
	//https://hc.apache.org/httpcomponents-client-ga/quickstart.html
	//http://blog.naver.com/PostView.nhn?blogId=marnet&logNo=220501038688&parentCategoryNo=&categoryNo=1&viewDate=&isShowPopularPosts=true&from=search
	
	private String strResult = "";
	
	private String url = "";
	
	////post_type = 2;	// 1 : default url parameter , 2 : json parameter
	public int post_type = 0;		//0 : get , 1 : post , 2 : json
	
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	private Map<String , String> headerMap = null;
	public Map<String , String> cookieMap	= new HashMap<String , String>();
	
	//프록시
	private HttpHost proxy = null;
	
	//request 옵션
	RequestConfig requestConfig = null;
	
	public HTTPUtil(){}
	
	public HTTPUtil(String url) {
		setUrl(url);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setHeader(Map<String , String> headerData) {
		this.headerMap = headerData;
	}
	
	private void setHeader(HttpMessage hm) {
		if(headerMap != null) {
			for( String key : headerMap.keySet() ){
				
				String value = headerMap.get(key);
				
				if(value.indexOf("json") > -1) {
					post_type = 2;
				}
				
				hm.setHeader(key, value);
	        }
		}
		
		if(cookieMap != null) {
			for( String key : cookieMap.keySet() ){
				
				String value = cookieMap.get(key);
				
				System.out.println("key = " + key);
				System.out.println("value = " + value);
				
				if(value.indexOf("json") > -1) {
					post_type = 2;
				}
				
				hm.setHeader(key, value);
	        }
		}
	}
	
	public void clearParameter() {
		params.clear();
	}
	
	public void setParameter(Map<String , Object> paramData) {
		
		if(paramData != null) {
			for( String key : paramData.keySet() ){
				
				Object value = paramData.get(key);
				
				if(value instanceof String) {
					params.add(new BasicNameValuePair(key , (String)paramData.get(key)) );
				}
				else if(value instanceof ArrayList) {
					
					ArrayList<HashMap<String,Object>> paramArray = (ArrayList<HashMap<String,Object>>)value;
					
					for(HashMap<String,Object> temp : paramArray) {
						setParameter(temp);
					}
				}
	        }
	    }
	}
	
	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}
	
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	public String httpPostSend(Map<String , Object> paramData) {
		
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response1 = null;
		try {
			
			if(proxy == null) {
				httpclient = HttpClients.createDefault();
			} else {
				httpclient = HttpClientBuilder.create().setProxy(proxy).build();
			}
			
			HttpPost post = new HttpPost(this.url);
			
			if(requestConfig != null) {
				post.setConfig(requestConfig);
			}
			
			setHeader(post);
			clearParameter();
			setParameter(paramData);

			if(post_type == 1 && params.size()>0) {
				HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		        post.setEntity(entity);
			} else if( post_type == 2 && params.size()>0) {
				JSONObject json = new JSONObject(paramData);
				System.out.println(json.toString());
				StringEntity postingString = new StringEntity(json.toString(),"utf-8");
		        post.setEntity(postingString);
		        post.setHeader("Content-Type", "application/json");
			}

			response1 = httpclient.execute(post);
			
			Header[] schH = response1.getAllHeaders();
			
			for (Header h : schH) {
		        System.out.println(h.getName() + " : " + h.getValue());
		    }
			HeaderElementIterator H = new BasicHeaderElementIterator(response1.headerIterator("Set-Cookie"));
			String stringCookie = "";
			// --- get all Set-Cookie
			while(H.hasNext())
	        {
	            HeaderElement element = H.nextElement();
	            stringCookie+=element.getName()+"="+element.getValue()+";";
	        }
			if(stringCookie!=null && stringCookie.length() > 0)
			{
				cookieMap.put("cookie", stringCookie);
			}
			
	        this.strResult = EntityUtils.toString(response1.getEntity());
	        
	        System.out.println(this.strResult);
	        EntityUtils.consume(response1.getEntity());
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(response1  != null) try{response1.close();}catch(Exception e){}
			if(httpclient != null) try{httpclient.close(); } catch (IOException ioe) {}
		}
		return this.strResult;
	}
	
	public HttpEntity httpGetSendImage(Map<String , Object> paramData) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response1 = null;
		HttpEntity entity1 = null;
		try {
			
			if(proxy == null) {
				httpclient = HttpClients.createDefault();
			} else {
				httpclient = HttpClientBuilder.create().setProxy(proxy).build();
			}
			
			clearParameter();
			setParameter(paramData);
			
			URI uri = new URIBuilder(this.url)
				    //.addParameter("firstParam", "A")
				    //.addParameter("secondParam", "B")
				    .addParameters(params)
				    .build();
			
			HttpGet httpGet = new HttpGet(uri);
			
			if(requestConfig != null) {
				httpGet.setConfig(requestConfig);
			}
			
			setHeader(httpGet);
			
			response1 = httpclient.execute(httpGet);
		
			entity1 = response1.getEntity();
			
			
		    
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    if(response1  != null) try{response1.close();}catch(Exception e){}
		    if(httpclient != null) try{httpclient.close(); } catch (IOException ioe) {}
		}
		
		return entity1;
	}
	
	public String httpGetSend(Map<String , Object> paramData) {
		
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response1 = null;
		
		try {
			
			if(proxy == null) {
				httpclient = HttpClients.createDefault();
			} else {
				httpclient = HttpClientBuilder.create().setProxy(proxy).build();
			}
			
			clearParameter();
			setParameter(paramData);
			
			URI uri = new URIBuilder(this.url)
				    //.addParameter("firstParam", "A")
				    //.addParameter("secondParam", "B")
				    .addParameters(params)
				    .build();
			
			HttpGet httpGet = new HttpGet(uri);
			
			if(requestConfig != null) {
				httpGet.setConfig(requestConfig);
			}
			
			setHeader(httpGet);
			
			response1 = httpclient.execute(httpGet);
			
			HeaderElementIterator H = new BasicHeaderElementIterator(response1.headerIterator("Set-Cookie"));
			String stringCookie = "";
			// --- get all Set-Cookie
			while(H.hasNext())
	        {
	            HeaderElement element = H.nextElement();
	            
	            System.out.println("get = " + element.getValue());
	            
	            stringCookie+=element.getName()+"="+element.getValue()+";";
	        }
			if(stringCookie!=null && stringCookie.length() > 0)
			{
				cookieMap.put("cookie", stringCookie);
			}
		
			HttpEntity entity1 = response1.getEntity();
			
			this.strResult = EntityUtils.toString(entity1);
			
		    //System.out.println(this.strResult);
		    EntityUtils.consume(entity1);
		    
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    if(response1  != null) try{response1.close();}catch(Exception e){}
		    if(httpclient != null) try{httpclient.close(); } catch (IOException ioe) {}
		}
		return this.strResult;
	}
	
}
