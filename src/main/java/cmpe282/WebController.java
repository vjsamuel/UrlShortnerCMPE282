package cmpe282;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebController {

	@Autowired
	UrlMapRepository repository;

	@RequestMapping("/deleteall")
	public String delete() {
		repository.deleteAll();
		return "Done";
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/api/v1/url/{shortURL}")
    public ResponseEntity<UrlMap> deleteURL(@RequestHeader(value="X-Forwarded-User") String user, @PathVariable String shortURL) {
    	if (user.equals(null) || user.isEmpty() == true) {
    		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    	}
    	
		try {
			UrlMap map = repository.findBysURL(shortURL).get(0);

			if (!map.getUser().equals(user)) {
				// User is different
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			String id = repository.findBysURL(map.getsURL()).get(0).getId();
			repository.delete(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
			
		} catch (Exception e) {
			e.getMessage();

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
    }

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/url/{shorturl}")
	  public ResponseEntity<String> expandUrl(@PathVariable String shorturl){
	    if(repository.findBysURL(shorturl).isEmpty()) {
	      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	    }   
	    
	    HttpHeaders header = new HttpHeaders();
	    header.add("Location", repository.findBysURL(shorturl).get(0).getoURL());
	    return new ResponseEntity<>(header, HttpStatus.PERMANENT_REDIRECT);
	  }
	
	
	 @RequestMapping(method = RequestMethod.PUT, value="/api/v1/url/shorten/{shortURL}") 
	    public ResponseEntity<UrlMap> putURL (@RequestHeader(value="X-Forwarded-User") String user, @RequestBody UrlMap input, @PathVariable String shortURL){
	    	if (user.equals(null) || user.isEmpty() == true) {
	    		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	    	}
	    	try {
	    			String oURL = input.getoURL();
	    			String sURL = shortURL;
	    			
	    			if (input.getoURL() == null) {
	    				return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    			}
	    			
	    			// Find object by short URL
	    			List<UrlMap> repositoryList = repository.findBysURL(sURL);
	    		
	    			    			
	    			for(UrlMap urlmap_object: repositoryList) {
	    				// Found the object
	    				if (urlmap_object.getsURL().equals(sURL)) {
	    					// User is the same as the user trying to update
	    					if (urlmap_object.getUser().equals(user)) {
	    						repository.delete(urlmap_object.getId());
	    						
	    						UUID uid = UUID.randomUUID();
	    						String id = uid.toString();
	    						// Do update
	    						UrlMap putUrlMap = new UrlMap(id, oURL, sURL, user);
	    		    			repository.save(putUrlMap);
	    		    		
	    		    			return new ResponseEntity<>(putUrlMap, HttpStatus.ACCEPTED); //202
	    					} else {
	    						// User is different
	    						return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	    					}
	    				}
	    			}
	    			
	    			// Object is not found
	    			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	    		} catch(Exception e) {
	    			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    		}
	    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/url/shorten")
	public ResponseEntity<UrlMap> shortenURL(@RequestHeader(value="X-Forwarded-User") String user, @RequestBody UrlMap input) {
		if (input.getoURL() == null || input.getoURL().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		try {
		String oURL = input.getoURL();
		if (!oURL.startsWith("https") && !oURL.startsWith("http")) {
			oURL = "http://" + oURL;
		}
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		String sURL = getHash(oURL);
		if (!repository.findBysURL(sURL).isEmpty()) {
			sURL = getHash(oURL + id);
		}
		
		UrlMap entry = new UrlMap(id, oURL, sURL, user);
		repository.save(entry);
		return new ResponseEntity<>(entry, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/findall")
	public String findAll() {
		String result = "";
		Iterable<UrlMap> urls = repository.findAll();

		for (UrlMap url : urls) {
			result += url.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") String id) {
		String result = "";
		result = repository.findOne(id).toString();
		return result;
	}

	@RequestMapping("/save") 
	public String save() {
		UrlMap urlMap = new UrlMap(System.currentTimeMillis()+ "", "http://www.google.com", "http://g.com", "Swetha" );
		repository.save(urlMap);
		return urlMap.toString();
	}
	
	public static String getHash(String longurl) {
         // Moving to SHA-512 as it is the secure Hash algorithm:
       try {
               MessageDigest md = MessageDigest.getInstance("SHA-512");
               byte[] sourceurl = md.digest(longurl.getBytes());
               BigInteger number = new BigInteger(1, sourceurl);
               String hashtext = number.toString(16);
               // Now we need to zero pad it if you actually want the full 32 chars.
               while (hashtext.length() < 32) {
                   hashtext = "0" + hashtext;
               }
               return hashtext.substring(0, 7);
         } catch (NoSuchAlgorithmException e) {
               throw new RuntimeException(e);
           }
       }

}
