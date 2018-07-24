package retail_store.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.parser.ParseException;

import retail_store.dao.dbconnectivity;

@Path("/register")
public class register {
	@Path("/add")
	@POST  
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response getMsg(String msg) throws ParseException { 
		dbconnectivity Dao = new dbconnectivity();
	      String value = Dao.register_add(msg);
	      return Response.status(200).entity(value).build();  
	  }
}
