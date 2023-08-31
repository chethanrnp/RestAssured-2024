package pojo.get.cources;

import java.util.List;

public class pojoGetCourses {

	private String instructor;
	private String url;
	private String services;
	private String expertise;
	private Courses courses;
	private String linkedIn;

	public pojoGetCourses() {

	}

	public pojoGetCourses(String instructor, String url, String services, String expertise, Courses courses,
			String linkedIn) {
		this.instructor = instructor;
		this.url = url;
		this.services = services;
		this.expertise = expertise;
		this.courses = courses;
		this.linkedIn = linkedIn;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
