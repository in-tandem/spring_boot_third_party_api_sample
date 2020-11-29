package com.org.somak.event_api.config;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

	private static final String KEYSTORE_PASSWORD_CANNOT_BE_EMPTY = "Key store password cannot be empty";
	private static final String KEYSTORE_PATH_CANNOT_BE_EMPTY = "Key store path cannot be empty";

	
	@Autowired
	private ApiConfig apiConfig;

	
	/***
	 * Single point of source for establishing rest connection with external endpoints
	 * @return RestTemplate instance
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	@Bean
	@Qualifier("endPointService")
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
	
		return apiConfig!=null && apiConfig.isSsl()?  sslEnabledRestTemplate(): sslDisabledRestTemplate();
	}
	
	/***
	 * Setting up custom behavior for the number of  connections to retain in a pool overall
	 * and connections to retain per host
	 * @return
	 */
	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		manager.setMaxTotal(apiConfig.getMaxNumberOfConnectionsPerHost());
		manager.setDefaultMaxPerRoute(apiConfig.getMaxNumberOfConnectionsPerHost());		
		return manager;
	}
	
	/**
	 * Setting up custom behavior for the timeout settings to be used application wide
	 * when connecting with external endpoint
	 * <br><br>
	 * For now setting up socket/connection timeouts same.
	 * @return
	 */
	@Bean
	public RequestConfig requestConfig() {
		
		return RequestConfig
				.custom()
				.setConnectionRequestTimeout(apiConfig.getConnectionRequestTimeoutInMilliSeconds())
				.setSocketTimeout(apiConfig.getConnectionRequestTimeoutInMilliSeconds())
				.setConnectTimeout(apiConfig.getConnectionRequestTimeoutInMilliSeconds())
				.build();
	}
	
	private RestTemplate sslEnabledRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		
		Objects.requireNonNull(apiConfig.getKeyStorePassword(), KEYSTORE_PASSWORD_CANNOT_BE_EMPTY);
		Objects.requireNonNull(apiConfig.getKeyStorePath(), KEYSTORE_PATH_CANNOT_BE_EMPTY);
		
		SSLContext sslContext = SSLContextBuilder
		        					.create()
							        .loadTrustMaterial(Paths.get(apiConfig.getKeyStorePath()).normalize().toFile(), apiConfig.getKeyStorePassword().toCharArray())
							        .build();
		
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
		HttpComponentsClientHttpRequestFactory requestFactory =	new HttpComponentsClientHttpRequestFactory();
		
		requestFactory.setHttpClient(getHttpClient(sslConnectionSocketFactory));
				
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(getMessageConverter());
		
		return restTemplate;
		
	}
	
	private RestTemplate sslDisabledRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		 
		SSLContext sslContext = org.apache.http.ssl.SSLContexts
												.custom()
												.loadTrustMaterial(null, acceptingTrustStrategy)
												.build();

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);

		HttpComponentsClientHttpRequestFactory requestFactory =	new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(getHttpClient(sslConnectionSocketFactory));
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(getMessageConverter());
		return restTemplate;
		
	}

	private MappingJackson2HttpMessageConverter getMessageConverter() {
		
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
		return mappingJackson2HttpMessageConverter;
	}
	
	private CloseableHttpClient getHttpClient(SSLConnectionSocketFactory sslConnectionSocketFactory) {
		
		return 
				HttpClientBuilder
				.create()
				.setSSLSocketFactory(sslConnectionSocketFactory)
				.setConnectionManager(poolingHttpClientConnectionManager())
				.setDefaultRequestConfig(requestConfig())
				.build();
		
	}
}
