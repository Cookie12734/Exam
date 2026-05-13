package bean;
//a
import java.io.Serializable;

public class ClassNum implements Serializable {

	private String classNum;

	private String className;

	private School school;

	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
