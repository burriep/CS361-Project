import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class Directory {

	private ArrayList<Employee> p = new ArrayList<Employee>();
	
	public Directory() {
		

	}
	
	public void addEmployee(String fn, String ln, String dep, String tel){
		p.add(new Employee(fn,ln,dep,tel));
		arrangeDirectory();
	}
	
	public void arrangeDirectory(){
		
		Collections.sort(p, new Comparator<Employee>(){
			public int compare(Employee e1, Employee e2){
				return e1.getLastName().compareTo(e2.getLastName());
			}
		});
	}
	
	public ArrayList<Employee> getList(){
		return p;
	}

}