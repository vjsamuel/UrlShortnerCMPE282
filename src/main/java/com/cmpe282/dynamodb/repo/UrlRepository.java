package com.javasampleapproach.dynamodb.repo;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.dynamodb.model.UrlMap;

@EnableScan
public interface UrlRepository extends CrudRepository<UrlMap, String> {

	public List<UrlMap> findBysUrl(String sUrl);
	public List<UrlMap> findByoUrl(String oUrl);
	public List<UrlMap> findByuserName(String userName);
	public List<UrlMap> findAll();
}
