package bean;
//a
import java.io.Serializable;

public class Subject extends User implements Serializable {
	private String SubjectCd;
	
	private String SubjectName;
	
	private String SchoolCd;

	public String getSubjectCd() {
		return SubjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		SubjectCd = subjectCd;
	}

	public String getSubjectName() {
		return SubjectName;
	}

	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}

	public String getSchoolCd() {
		return SchoolCd;
	}

	public void setSchoolCd(String schoolCd) {
		SchoolCd = schoolCd;
	}
}

