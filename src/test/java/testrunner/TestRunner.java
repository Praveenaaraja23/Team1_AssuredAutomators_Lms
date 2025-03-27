package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
			features = {"src/test/resources/Features"}, 
			glue = {"stepDefinitions","hooks"},
			tags ="@login or @batch or @DeleteBatch",
			plugin = {"pretty", "html:target/Team1_LMSReport.html","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
			
		)
	

	public class TestRunner extends AbstractTestNGCucumberTests {
		
	  
	}


