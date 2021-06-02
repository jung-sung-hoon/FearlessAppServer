package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.code.EventCategory;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProxy {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Test
    public void test_proxy() {
    	
    	try {
    		//Creating the CredentialsProvider object
            CredentialsProvider credsProvider = new BasicCredentialsProvider();

            //Setting the credentials
            credsProvider.setCredentials(new AuthScope("www.naver.com", 443), 
               new UsernamePasswordCredentials("user", "mypass"));
            credsProvider.setCredentials(new AuthScope("34.64.169.221", 80), 
               new UsernamePasswordCredentials("abc", "passwd"));

            //Creating the HttpClientBuilder
            HttpClientBuilder clientbuilder = HttpClients.custom();

            //Setting the credentials
            clientbuilder = clientbuilder.setDefaultCredentialsProvider(credsProvider);
            
            //Building the CloseableHttpClient object
            CloseableHttpClient httpclient = clientbuilder.build();


            //Create the target and proxy hosts
            HttpHost targetHost = new HttpHost("www.naver.com", 443, "https");
            HttpHost proxyHost = new HttpHost("34.64.169.221", 80, "https");

            //Setting the proxy
            RequestConfig.Builder reqconfigconbuilder= RequestConfig.custom();
            reqconfigconbuilder = reqconfigconbuilder.setProxy(proxyHost);
            RequestConfig config = reqconfigconbuilder.build();

            //Create the HttpGet request object
            HttpGet httpget = new HttpGet("/");

            //Setting the config to the request
            httpget.setConfig(config);
       
            //Printing the status line
            HttpResponse response = httpclient.execute(targetHost, httpget);
            System.out.println(response.getStatusLine());
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	

    }
    	
    	
    
    
    


}
