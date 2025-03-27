package models;

import java.util.List;

public class UpdateClass_Pojo {
	private int batchId;
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
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getClassNo() {
		return classNo;
	}
	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}
	public String getClassDate() {
		return classDate;
	}
	public void setClassDate(String classDate) {
		this.classDate = classDate;
	}
	public String getClassTopic() {
		return classTopic;
	}
	public void setClassTopic(String classTopic) {
		this.classTopic = classTopic;
	}
	public String getClassStatus() {
		return classStatus;
	}
	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}
	public String getClassStaffId() {
		return classStaffId;
	}
	public void setClassStaffId(String classStaffId) {
		this.classStaffId = classStaffId;
	}
	public String getClassDescription() {
		return classDescription;
	}
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	public String getClassComments() {
		return classComments;
	}
	public void setClassComments(String classComments) {
		this.classComments = classComments;
	}
	public String getClassNotes() {
		return classNotes;
	}
	public void setClassNotes(String classNotes) {
		this.classNotes = classNotes;
	}
	public String getClassRecordingPath() {
		return classRecordingPath;
	}
	public void setClassRecordingPath(String classRecordingPath) {
		this.classRecordingPath = classRecordingPath;
	}
	public List<String> getClassScheduledDates() {
		return classScheduledDates;
	}
	public void setClassScheduledDates(List<String> classScheduledDates) {
		this.classScheduledDates = classScheduledDates;
	}
}
