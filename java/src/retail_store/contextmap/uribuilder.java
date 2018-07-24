package retail_store.contextmap;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class uribuilder {
	public static void main(String[] args) {
	    ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    WebTarget target = client.target(getBaseURI());
	    System.out.println("Config"+config);
	    System.out.println("Client"+client);
	    System.out.println("target"+target);
//	      System.out.println(target.path("api").path("get").request().accept(MediaType.APPLICATION_JSON).get(String.class));
	      System.out.println(target.path("register").path("add").request().accept(MediaType.APPLICATION_JSON).get(String.class));
	      System.out.println(target.path("login").path("verify").request().accept(MediaType.APPLICATION_JSON).get(String.class));
	      System.out.println(target.path("products").path("view").request().accept(MediaType.APPLICATION_JSON).get(String.class));
	  }
//	.path("add")
	  private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://localhost:8080/retail_store").build();
	  }
}
