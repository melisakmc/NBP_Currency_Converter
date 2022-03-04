package controller;

import static org.springframework.http.HttpStatus.OK;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import model.CurrencyExchange;
import model.GoldPrice;
import model.WorkDay;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
public class NBPController {

	//returns currency exchange rate 
	@GetMapping("/api/exchange-rates/{currencyCode}")
	public ResponseEntity<List<CurrencyExchange>> getCurrencyExch(@PathVariable("currencyCode") String currencyCode) throws IOException, ParseException {
		
		try {
			LocalDate currentDate = java.time.LocalDate.now();
			WorkDay workDay = new WorkDay();
			LocalDate startDate = workDay.getDate(currentDate, 5);	//getting the date of 5 work days ago
			URL url = new URL("https://api.nbp.pl/api/exchangerates/rates/a/"+currencyCode+"/"+
								startDate+"/"+currentDate+"/?format=json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();	//connecting
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {				//if response code is 200
				List<CurrencyExchange> list = new ArrayList<CurrencyExchange>();
				Scanner scanner = new Scanner(url.openStream());
				StringBuilder string = new StringBuilder();
				while(scanner.hasNext()) string.append(scanner.nextLine());		//getting response
				scanner.close();
				JSONParser parser = new JSONParser();							
				JSONObject info = (JSONObject) parser.parse(String.valueOf(string));
				JSONArray arr = (JSONArray) info.get("rates");				//getting "rates" section from the json response
				for (int i = 0; i<arr.size(); i++) {
					JSONObject newJSON = (JSONObject)arr.get(i);
					CurrencyExchange newExch = new CurrencyExchange(currencyCode,(Double)newJSON.get("mid"),(String)newJSON.get("effectiveDate"));
					list.add(newExch);			//parsing each entry as "CurrencyExchange"
				}
				return new ResponseEntity(list,OK);
			}
			else throw new RuntimeException("HttpResponseCode: " +responseCode);
			}
		catch (MalformedURLException ex) {
	        throw new RuntimeException(ex);
	    }	
	}
	
	@GetMapping("/api/gold-price/average")
	public ResponseEntity<List<GoldPrice>> getAvgGoldPrice() throws ParseException, IOException {
		
		try {
			LocalDate currentDate = java.time.LocalDate.now();
			WorkDay workDay = new WorkDay();
			LocalDate startDate = workDay.getDate(currentDate, 14);		//getting the date of 14 work days ago
			URL url = new URL("https://api.nbp.pl/api/cenyzlota/"+startDate+
								"/"+currentDate+"/?format=json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();     //connecting
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {			//if response code is 200
				List<GoldPrice> list = new ArrayList<GoldPrice>();
				Scanner scanner = new Scanner(url.openStream());
				StringBuilder string = new StringBuilder();
				while(scanner.hasNext()) string.append(scanner.nextLine());		//getting response
				scanner.close();
				JSONParser parser = new JSONParser();
				JSONArray arr = (JSONArray) parser.parse(String.valueOf(string));
				for (int i = 0; i<arr.size(); i++) {
					JSONObject newJSON = (JSONObject)arr.get(i);
					GoldPrice newExch = new GoldPrice((String)newJSON.get("data"),(Double)newJSON.get("cena"));
					list.add(newExch);		//parsing each entry as "GoldPrice"
				}
				return new ResponseEntity(list,OK);
			}
			else throw new RuntimeException("HttpResponseCode: " +responseCode);
			}
		catch (MalformedURLException ex) {
	        throw new RuntimeException(ex);
	    }	
	}
}