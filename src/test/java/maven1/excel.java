package maven1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import org.json.simple.*;
import static io.restassured.RestAssured.*;

public class excel {

	public static void main(String a[]) {
		List<String> excel = new ArrayList<String>();
		Cell cell = null;
		Cell cellMarket = null;
		Cell cellcountry = null;

		File src = new File("C:\\Users\\Fleek\\Downloads\\ReadExcelFile.xlsx");
		try {
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = wb.getSheetAt(0);
			Row rows = sheet1.getRow(0);
			int count = rows.getLastCellNum();
			/*
			 * String data = sheet1.getRow(2).getCell(1).getStringCellValue();
			 * System.out.println(data);
			 */

			int row = sheet1.getLastRowNum();
		

			for (int i = 1; i <= row; i++) {
				String data1 = sheet1.getRow(i).getCell(0).getStringCellValue();
				excel.add(data1);

			}
			System.out.println(excel);
			int colNum1 = 0;
			int colNum1Market = 0;
			int colNum1Country = 0;
			int colNum1active = 0;
			int colNum1marketplace=0;
			int colNum1plan=0;
			int colNum1State=0;

			rows = sheet1.getRow(0);
			for (int i = 0; i < count; i++) {
				if (rows.getCell(i).getStringCellValue().trim().equals("Subscription_state")) {
					colNum1 = i;

				}
				if (rows.getCell(i).getStringCellValue().trim().equals("PrimaryMarketplace")) {
					colNum1Market = i;
				}
				if (rows.getCell(i).getStringCellValue().trim().equals("PrimaryCountry")) {
					colNum1Country = i;
				}
				if (rows.getCell(i).getStringCellValue().trim().equals("Associated_device subscription_active")) {
					colNum1active = i;
				}
				if (rows.getCell(i).getStringCellValue().trim().equals("Associated_device marketplace")) {
					colNum1marketplace = i;
				}
				if (rows.getCell(i).getStringCellValue().trim().equals("Associated_device subscription_plan")) {
					colNum1plan = i;
				}
				if (rows.getCell(i).getStringCellValue().trim().equals("Associated_device subscription_state")) {
					colNum1State= i;
				}
				
				
				
				
				
			

			}

			/*
			 * for (int j = 0; j < count; j++) {
			 * if(rows.getCell(j).getStringCellValue().trim().equals("PrimaryMarketplace"))
			 * { colNum1Market = j; }
			 * 
			 * 
			 * }
			 */

			System.out.println("data");
			int m = 1;
			for (String element : excel) {
				System.out.println(element);
				RestAssured.baseURI = "https://api.staging.lingokids.io";

				RequestSpecification request = RestAssured.given();

				JSONObject requestParams = new JSONObject();
				requestParams.put("grant_type", "client_credentials");
				requestParams.put("client_id", "9c_qODO2GKraJixccKJYgChDsN_GCR9FNT_KRbA9J0o");
				requestParams.put("client_secret", "JvWwh2QK_bavSw9Z8mVEGORGkE-T_NDMJ-OYUk0doB4");
				requestParams.put("scope", "read:users");

				request.header("Content-Type", "application/json");
				request.body(requestParams.toJSONString());

				Response response = request.post("/oauth/token");
				System.out.println(response.getBody().asString());

				Object abc = JSONValue.parse(response.getBody().asString());
				JSONObject jsonObject = (JSONObject) abc;
				String token = (String) jsonObject.get("access_token");
				System.out.println(token);

				Response response1 = given().queryParam("email", element).header("Authorization", "Bearer " + token)
						.when().get("/v1/public/users");
				System.out.println(response1.getBody().asString());

				// for (int i = 1; i<=count; i++) {
				Object abc1 = JSONValue.parse(response1.getBody().asString());
				JSONObject jsonObject1 = (JSONObject) abc1;
				String subscription = (String) jsonObject1.get("subscription_state");
				System.out.println(subscription);

				String PrimaryMarket = (String) jsonObject1.get("marketplace");
				System.out.println(PrimaryMarket);

				String PrimaryCountry = (String) jsonObject1.get("country");
				System.out.println(PrimaryCountry);
				
				
				
				
		

				rows = sheet1.getRow(m++);
				cell = rows.createCell(colNum1);
				cell.setCellValue(subscription);

				cellMarket = rows.createCell(colNum1Market);
				cellMarket.setCellValue(PrimaryMarket);

				cellcountry = rows.createCell(colNum1Country);
				cellcountry.setCellValue(PrimaryCountry);

				FileOutputStream fo = new FileOutputStream("C:\\\\Users\\\\Fleek\\\\Downloads\\\\ReadExcelFile.xlsx");
				wb.write(fo);

				fo.close();
				
			
				
				

				// }

			
	
			//convert JSON to string
			
			Object abc11 = JSONValue.parse(response1.getBody().asString());
			JSONObject jsonObject11 = (JSONObject) abc11;
		      JsonPath j = new JsonPath(response1.asString());
		      int s = 0;
		      try {
		    	  s = j.getInt("associated_devices.size()");
		      }
		      catch(Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error" + e.getMessage());
				
		    	  
		      }
			 
		      for(int i = 0; i < s; i++) {
		         String subscription_active = j.getString("associated_devices["+i+"].subscription_active");
		         String marketplace = j.getString("associated_devices["+i+"].marketplace");
		         String subscription_state = j.getString("associated_devices["+i+"].subscription_state");
		         String subscription_plan = j.getString("associated_devices["+i+"].subscription_plan");
		         
		         System.out.println(subscription_active);
		         System.out.println(marketplace);
		         System.out.println(subscription_state);
		         System.out.println(subscription_plan);
		         
			      //rows = sheet1.getRow(m++);
		         if(subscription_active.equals("true")) {
		        	 
						cell = rows.createCell(colNum1active);
						cell.setCellValue(subscription_active);
						
						cell = rows.createCell(colNum1marketplace);
						cell.setCellValue(marketplace);
						
						cell = rows.createCell(colNum1plan);
						cell.setCellValue(subscription_plan);
						
						cell = rows.createCell(colNum1State);
						cell.setCellValue(subscription_state);
						
						FileOutputStream fo1 = new FileOutputStream("C:\\\\Users\\\\Fleek\\\\Downloads\\\\ReadExcelFile.xlsx");
						wb.write(fo1);

						fo1.close();
						break;
		         }

		         
		      }

				
				
				
		      
		      
			}
			
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error" + e.getMessage());
		}

	}
}

