package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
			features = {"src/test/resources/Features/"}, 
			glue = {"stepDefinitions","hooks"},
					tags ="@login or @ProgramModule",
			//tags ="@login  or @ProgramModule or @batch or @GetBatches or @GetBatchByBatchId or  @UpdateBatchByBatchId or @GetBatchByBatchName or @class2 or @class",
			plugin = {"pretty", "html:target/Team1_LMSReport.html","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
			
		)
	

	public class TestRunner extends AbstractTestNGCucumberTests {
		
	  
	}


