package jaxquotes;


import java.io.*;

import javax.json.*;
import javax.ws.rs.*;


@Path("/quotes")
public class Quotes {
	
	static final String FILE = "/Users/Michael/workspace/JaxQuotes/quotes.json";

@GET
@Produces({"text/html"})
public String getQuotes(){
	String htmlString = "<html><body>";
	try {
		JsonReader reader = Json.createReader(new StringReader(getQuotesJSON()));
		JsonObject rootObj = reader.readObject();
		JsonArray array = rootObj.getJsonArray("quotes");
		
		
		for(int i = 0 ; i < array.size(); i++) {
			JsonObject obj = array.getJsonObject(i);
			htmlString += "<b>Author : " + obj.getString("author") + "</b><br>";
			htmlString += "Quote : " + obj.getString("quote") + "<br>";
			
			htmlString += "<br><br>";
		}
	}
	catch(Exception ex) {
		htmlString = "<html><body>" + ex.getMessage();
	}
	
	return htmlString + "</body></html>";
}

@GET
@Produces({"application/json"})
public String getQuotesJSON() {
	String jsonString = "";
	try {
		InputStream fis = new FileInputStream(FILE);
        JsonReader reader = Json.createReader(fis);
        JsonObject obj = reader.readObject();
        reader.close();
        fis.close();
        
        jsonString = obj.toString();
	} 
	catch (Exception ex) {
		jsonString = ex.getMessage();
	}
	
	return jsonString;
}	

@GET
@Path("{author}")
@Produces({"text/html"})
public String getProductJSON(@PathParam("author") String author) {
	String htmlString = "<html><body>";
	try {
		JsonReader reader = Json.createReader(new StringReader(getQuotesJSON()));
		JsonObject rootObj = reader.readObject();
		JsonArray array = rootObj.getJsonArray("quotes");
		
		
		for(int i = 0 ; i < array.size(); i++) {
			JsonObject obj = array.getJsonObject(i);
			if(obj.getString("author").equalsIgnoreCase(author)){
				htmlString += "<b>Author : " + obj.getString("author") + "</b><br>";
				htmlString += "Quote : " + obj.getString("quote") + "<br>";
			}
			
			htmlString += "<br><br>";
		}
	}
	catch(Exception ex) {
		htmlString = "<html><body>" + ex.getMessage();
	}
	
	return htmlString + "</body></html>";
}
}
