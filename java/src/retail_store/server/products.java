package retail_store.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.parser.ParseException;

import retail_store.dao.dbconnectivity;
@Path("/products")
public class products {
	@Path("/view")
	@GET  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getMsg() throws ParseException { 
		dbconnectivity Dao = new dbconnectivity();
	      String value = Dao.product_view();
	      return Response.status(200).entity(value).build();  
	  }
}
