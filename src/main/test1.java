package main;

import java.io.File;

import TestPath2Webdriver.WebdriverCommand;

public class test1 {
	public static boolean runTestCase(String filename, String url, int delay, boolean islogging){
		boolean b1 = false;
		try{
			
			WebdriverCommand webcom = new WebdriverCommand("abc",  new File(filename));
			
			System.out.println("FILE OK");
			
			b1 = webcom.runTestCaseWithUrl(url, delay, islogging);
			webcom.quitDriver();
			
		} catch (Exception e){
			
		}
		
		return b1;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		boolean b1 = true;
		
		b1 = runTestCase("D:\\Dropbox\\NCKH\\code\\state_event_element_mapping.xls",
				"http://localhost:8080/nckh/login.jsp", 00, true);
		
		if (!b1){
			System.out.println("FAIL");
		} else {
//			b1 = runTestCase("D:\\Dropbox\\coltech\\KLTN\\code\\state_event_element_mapping_main_html.xls", 
//				"http://localhost:8080/kltn/main.jsp?user=user1&password=pass1&submit=login",
//				100, true);
		}
		
		if (b1){
			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}
		//state_event_element_mapping_main_html.xls
		
	}
}
