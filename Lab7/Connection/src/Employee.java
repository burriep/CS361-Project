

public class Employee {
	private String firstName;
	private String lastName;
	private String department;
	private String phone;

	public Employee(String fn, String ln, String dept, String tel) {
		firstName = fn;
		lastName = ln;
		department = dept;
		phone = tel;
	}
	public void setName(String fn) {
		firstName = fn;
	}

	public String getName(){
		return firstName;
	}

	public void setLastName(String ln) {
		lastName = ln;
	}
	public String getLastName(){
		return lastName;
	}

	public void setDept(String dept){
		department = dept;
	}

	public String getDept(){
		return department;
	}

	public void setPhone(String tel) {
		phone = tel;
	}

	public String getPhone(){
		return phone;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName +" " + department +" "+ phone  ;
	}
}
