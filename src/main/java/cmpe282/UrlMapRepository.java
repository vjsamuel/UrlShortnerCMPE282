package cmpe282;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import cmpe282.UrlMap;

@EnableScan
public interface UrlMapRepository extends CrudRepository<UrlMap, String> {

	public List<UrlMap> findBysURL(String sURL);
	public List<UrlMap> findByoURL(String oURL);
	public List<UrlMap> findByuser(String user);
	public List<UrlMap> findAll();

}
