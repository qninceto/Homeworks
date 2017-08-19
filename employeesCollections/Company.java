package employeesCollections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Company {
	private int numberOfDepartments;
	private List<Set<Employee>> departments;// v dva razlichni seta li da sa --> treeset i hashset 

	public Company(int numberOfDepartments) {
		this.setNumberOfDepartments(numberOfDepartments);
		departments = new ArrayList<Set<Employee>>(numberOfDepartments);
		for (int index = 0; index < numberOfDepartments; index++) {//s takyv cikyl li da gi syzdam
			departments.add(new TreeSet<Employee>());
//			departments.add(new HashSet<Employee>());
		}
	}

	void hire(String name, String phone, float salary, int depNum) {
		//mozhe li edin i sy6ti rabotnik da e ednovremenno v nqkolko otdela?
		//ako ne ---->proverka s 2 for loopa, dali nqkoj treeSet ne go sydyrzha veche
		int arrayNoDepartment = depNum - 1;
		if(arrayNoDepartment < this.departments.size()){
			this.departments.get(arrayNoDepartment).add(new Employee(name, phone, salary));
		}
		//else throw new exception
	}

	void spravka() {// raboti i za dvata vida set
		int a = 1; /* ???ima li nqkyv po-malko maloumen nachin da indexiram
					 otdelite na konzolata????*/
		for (Iterator<Set<Employee>> it = departments.iterator(); it.hasNext();) {
			System.out.println("department number: " + a++);
			Set<Employee> department = it.next();// shto ne trqbva da castvam, nali next() vry6ta object????
			if (department.isEmpty())
				System.out.println("toz otdel nema nikoj, maj traa go zakrie6...");
			else {
				for (Employee employee : department)
					System.out.println(employee);
			}
			System.out.println();
		}
	}

	public void setNumberOfDepartments(int numberOfDepartments) {
		if (numberOfDepartments > 0)
			this.numberOfDepartments = numberOfDepartments;
		// else throw new exception
	}

}