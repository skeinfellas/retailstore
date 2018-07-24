package retail_store.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class dbconnectivity {

	public String register_add(String val) throws ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject values = (JSONObject) parser.parse(val);
		JSONObject response = new JSONObject();
		
		try{ 
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore","root","root");  
			Statement stmt=con.createStatement();
//			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			stmt.executeUpdate("insert into userdetails (name,email,phonenumber,password,empid,dealerid,time,usertype) values('"+values.get("name")+"','"+values.get("email")+"','"+values.get("phone")+"','"+values.get("password")+"','"+values.get("empid")+"','"+values.get("dealerid")+"','"+timeStamp+"','"+values.get("usertype")+"')");
			response.put("code",200);
			response.put("message","Registered successfully");
			
		}
		catch (Exception e){
			System.out.println("Error"+e);
			response.put("code",400);
			response.put("message","Registered successfully");
		}
		return response.toJSONString();
	}
	
	public String login_verify(String val) throws ParseException{
		JSONObject response = new JSONObject();
		
		JSONParser parser = new JSONParser();
		JSONObject values = (JSONObject) parser.parse(val);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore","root","root");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from userdetails where phonenumber = '"+values.get("phone")+"' AND password = '"+values.get("password")+"'"); 
			
			if(rs.next() == false)
			{
				response.put("code", 400);
				response.put("message", "Invalid Username and password ");
				response.put("payload", null);
			}else{
				if(rs.getString(9).equals("employee")){
					JSONObject value = new JSONObject();
					value.put("discount", 30);
					response.put("code", 200);
					response.put("message", "Login successfully");
					response.put("payload", value);
				}
				if(rs.getString(9).equals("thirdparty")){
					JSONObject value = new JSONObject();
					value.put("discount", 10);
					response.put("code", 200);
					response.put("message", "Login successfully");
					response.put("payload", value);
				}
				if(rs.getString(9).equals("customer")){
					JSONObject value = new JSONObject();
					Calendar cal = Calendar.getInstance();
					int presentyear = cal.get(Calendar.YEAR);
					Timestamp TimeStamp = Timestamp.valueOf(rs.getString(8));
					cal.setTimeInMillis(TimeStamp.getTime());
					int stored = cal.get(Calendar.YEAR);
					if((presentyear-stored) < 2){
						value.put("discount", 0);
					}else{
						value.put("discount", 5);
					}
					response.put("code", 200);
					response.put("message", "Login successfully");
					response.put("payload", value);
				}
			}
			
		}catch(Exception e){
			System.out.println("Login error"+e);
		}
		return response.toJSONString();
		
	}
	
	public String product_view() throws ParseException
	{
		JSONObject response = new JSONObject();
		ArrayList array = new ArrayList();
		try{ 
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore","root","root");  
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from products");
			if(!rs.isBeforeFirst()){
				response.put("code", 400);
				response.put("message","No Product is Available");
				response.put("payload", null);
				}else{
					while(rs.next() || rs.isLast()){
						JSONObject value = new JSONObject();
						value.put("productid",rs.getInt(1));
						value.put("productname",rs.getString(2));
						value.put("producttype",rs.getString(3));
						value.put("productprice",rs.getInt(4));
						array.add(value);
					}
					response.put("code", 200);
					response.put("message","All Product Obtained successfully");
					response.put("payload", array);
				}
			con.close();  
		}catch(Exception e){ System.out.println(e);}
	return response.toJSONString();
	}
	
}
