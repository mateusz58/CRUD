package tasks.helper;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientRestTemplateHelper {

	private static final Logger log = LoggerFactory.getLogger((ClientRestTemplateHelper.class));

	private RestTemplate restTemplate;

	private ObjectMapper objectMapper;

	public ClientRestTemplateHelper(RestTemplateBuilder restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate.build();
		this.objectMapper = objectMapper;
	}

	public <T> T getForEntity(Class<T> clazz, String url) {
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
			return readValue(response, javaType);
		} catch (HttpClientErrorException exception) {
			if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.info("No data found {}", url);
			} else {
				log.info("rest client exception", exception.getMessage());
			}
		}
		return null;
	}

	public <T> List<T> getForList(Class<T> clazz, String url) {
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
			return readValue(response, collectionType);
		} catch (HttpClientErrorException exception) {
			if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.info("No data found {}", url);
			} else {
				log.info("rest client exception", exception.getMessage());
			}
		}
		return null;
	}

	public <T> T postForObject(URI url, Class<T> clazz) {
		log.info("Attempt to post object with url {} and type {}",url, clazz.getName());
		return restTemplate.postForObject(url, null, clazz);
	}

	public <T,R> T postForEntity(Class<T> clazz, String url, R body) {
		HttpEntity<R> request = new HttpEntity<>(body);
		log.info("Attemp to add object with url {} and body {}",url, body.toString());
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
		return readValue(response, javaType);
	}

	private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
		T result = null;
		if (response.getStatusCode() == HttpStatus.OK ||
				response.getStatusCode() == HttpStatus.CREATED) {
			try {
				result = objectMapper.readValue(response.getBody(), javaType);
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		} else {
			log.info("No data found {}", response.getStatusCode());
		}
		return result;
	}
}
