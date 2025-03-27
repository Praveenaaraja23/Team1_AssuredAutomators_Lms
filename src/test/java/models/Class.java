package models;

import java.util.List;

public class Class {
	
	private int classNo;
	private String classDate;
	private String classTopic;
	private String classStatus;
	private String classStaffId;
	private String classDescription;
	private String classComments;
	private String classNotes;
	private String classRecordingPath;
	private List<String> classScheduledDates;
	private int batchId;
	
	
	public int getbatchId() {
		return batchId;
	}
	public void setbatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getclassNo() {
		return classNo;
	}
	public void setclassNo(int classNo) {
		this.classNo = classNo;
	}
	public String getclassDate() {
		return classDate;
	}
	public void setclassDate(String classDate) {
		this.classDate = classDate;
	}
	public String getclassTopic() {
		return classTopic;
	}
	public void setclassTopic(String classTopic) {
		this.classTopic = classTopic;
	}
	public String getclassStatus() {
		return classStatus;
	}
	public void setclassStatus(String classStatus) {
		this.classStatus = classStatus;
	}
	public String getclassStaffId() {
		return classStaffId;
	}
	public void setclassStaffId(String classStaffId) {
		this.classStaffId = classStaffId;
	}
	public String getclassDescription() {
		return classDescription;
	}
	public void setclassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	public String getclassComments() {
		return classComments;
	}
	public void setclassComments(String classComments) {
		this.classComments = classComments;
	}
	public String getclassNotes() {
		return classNotes;
	}
	public void setclassNotes(String classNotes) {
		this.classNotes = classNotes;
	}
	public String getclassRecordingPath() {
		return classRecordingPath;
	}
	public void setclassRecordingPath(String classRecordingPath) {
		this.classRecordingPath = classRecordingPath;
	}
	
	public List<String> getclassScheduledDates() {
		return classScheduledDates;
	}
	public void setclassScheduledDates(List<String> classScheduledDates) {
		this.classScheduledDates = classScheduledDates;
	}
}
