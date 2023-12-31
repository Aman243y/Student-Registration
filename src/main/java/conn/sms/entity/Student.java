package conn.sms.entity;

public class Student {
	private int sid;
	private String firstname;
	private String lastname;
	private String email;
	 
	public Student(int sid, String firstname, String lastname, String email) {
		super();
		this.sid = sid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		
		
		
	}
	public int getSid() {
		return sid;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getEmail() {
		return email;
	}
	
	
	
	

}
