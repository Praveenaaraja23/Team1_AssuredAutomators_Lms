package utilities;

import java.util.Map;

import models.Batch;

public class BatchDataMapper {
	
	public static Batch mapRowDataToBatch(Map<String, String> row) {
		Batch batch = new Batch();
		
		batch.setbatchDescription(row.get("BatchDescription"));
		batch.setbatchName(row.get("BatchName"));
		batch.setbatchNoOfClasses(row.get("NoOfClasses"));
		batch.setbatchStatus(row.get("BatchStatus"));
		batch.setprogramId(row.get("ProgramId"));// To do get Program id from context
		return batch; 
		
	}

}
