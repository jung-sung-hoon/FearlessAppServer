package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.code.EventCategory;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.service.EventsService;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.EventsVo;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProxy {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    //http://www.freeproxylists.net/
    
    //private static final String PROXY_HOST = "174.138.27.47";
    //private static final int PROXY_PORT = 8080;
    
    //private static final String PROXY_HOST = "167.99.52.144";
    //private static final int PROXY_PORT = 8080;
    
    //private static final String PROXY_HOST = "167.71.167.1";
    //private static final int PROXY_PORT = 8080;
    
    //private static final String PROXY_HOST = "165.22.199.170";
    //private static final int PROXY_PORT = 8080;
    
    private static final String PROXY_HOST = "209.97.153.244";
    private static final int PROXY_PORT = 8080;
    
    @Test
    public void test_proxy() {
    	
    	try {
    	
    		//////////////////////////////////////////////////////////////////
    		
    		HttpHost host = new HttpHost(PROXY_HOST, PROXY_PORT);
    		
    		
    		
    		//CloseableHttpClient client = HttpClientBuilder.create().setProxy(host).build();
    		
    		CloseableHttpClient client = HttpClients.createDefault();
    		
    		String url = "https://www.instagram.com/nyong2ya/";
    		//String url ="https://www.google.com";
    		//String url ="http://www.ridge.kr";
    		
    		URI uri = new URIBuilder(url)
				    //.addParameter("firstParam", "A")
				    //.addParameter("secondParam", "B")
				    //.addParameters(params)
				    .build();
    		
    		
    		RequestConfig requestConfig = RequestConfig.custom()
    				  .setConnectTimeout(1000*1000)
    				  .setConnectionRequestTimeout(1000*1000)
    				  .build();
    		
    		
    		HttpGet request = new HttpGet(uri);
    		request.setConfig(requestConfig);
    		
    		
    		CloseableHttpResponse httpResponse0 = client.execute(request);
    		
    		HttpEntity entity1 = httpResponse0.getEntity();
			
			String strResult = EntityUtils.toString(entity1);
			
			System.out.println("strResult = " + strResult);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    	
    	
    //@Test
    public void test_proxy2() {
    	
    	try {
    	
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    


}
