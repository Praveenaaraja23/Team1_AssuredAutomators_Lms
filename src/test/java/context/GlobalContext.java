package context;

import java.util.ArrayList;
import java.util.List;

public class GlobalContext {
    private static String token;
    private static List<Integer> programIds = new ArrayList<>();
    private static List<Integer> batchIds = new ArrayList<>();
    private static List<Integer> userIds = new ArrayList<>();
	private static List<Integer> studentIds = new ArrayList<>();
    private static List<Integer> staffIds = new ArrayList<>();
    private static List<Integer> roleIds = new ArrayList<>();
    
    
    
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
    
    public static void addUserId(int userId) {
        userIds.add(userId);
    }
    
    public static int getUserId(int index) {
        if (index >= 0 && index < userIds.size()) {
            return userIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for userIds");
    }
   
    public static void addStudentId(int studentId) {
        studentIds.add(studentId);
    }
    
    public static int getstudentId(int index) {
        if (index >= 0 && index < studentIds.size()) {
            return studentIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for studentIds");
    }
    
    public static void addStaffId(int staffId) {
        staffIds.add(staffId);
    }
    
    public static int getstaffId(int index) {
        if (index >= 0 && index < staffIds.size()) {
            return staffIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for staffIds");
    }
    
    public static void addRoleId(int roleId) {
        roleIds.add(roleId);
    }
    
    public static int getroleId(int index) {
        if (index >= 0 && index < roleIds.size()) {
            return roleIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index for roleIds");
    }
}