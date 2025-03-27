package context;

import java.util.ArrayList;
import java.util.List;

public class GlobalContext {
    private static String token;
    private static List<Integer> programIds = new ArrayList<>();
    private static List<Integer> batchIds = new ArrayList<>();

    private static List<Integer> classIds = new ArrayList<>();
    

    private static String batchName;
    
    public static String getBatchName() {
		return batchName;
	}

	public static void setBatchName(String batchName) {
		GlobalContext.batchName = batchName;
	}

	public static void setToken(String token) {

        GlobalContext.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static void addProgramId(int programId) {
        programIds.add(programId);
    }
    
    public static int getProgramId(int index) {
        if (index >= 0 && index < programIds.size()) {
            return programIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for programIds");
    }
    
    public static void addBatchId(int batchId) {
        batchIds.add(batchId);
    }
    
    public static int getBatchId(int index) {
        if (index >= 0 && index < batchIds.size()) {
            return batchIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for batchIds");
    }
    public static void addClassId(int classId) {
        classIds.add(classId);
    }
    
    public static int getClassId(int index) {
        if (index >= 0 && index < classIds.size()) {
            return classIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for classIds");
    }
}