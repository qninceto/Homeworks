package employeesCollections;

public class Employee implements Comparable<Employee> {
	private String name;
	private String phone;
	private float salary;

	public Employee(String name, String phone, float salary) {
		this.setName(name);
		this.setPhone(phone);
		this.setSalary(salary);
	}

	@Override
	public int compareTo(Employee o) {
		if(this.name == o .name)
			return (int) (this.name.compareTo(o.name));
		return (int) (this.salary - o.salary);
	}
	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", phone=" + phone + ", salary=" + salary + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + Float.floatToIntBits(salary);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
			return false;
		return true;
	}

//setters
	private void setName(String name) {
		if(name != null && name.trim().length()>0)
			this.name = name;
//		else
//			through exceptoion
	}

	private void setPhone(String phone) {
		if(phone != null && phone.trim().length() > 0)
			this.phone = phone;
//		else
//		through exceptoion
	}

	private void setSalary(float salary) {
		if(salary > 0)
			this.salary = salary;
//		else
//		through exceptoion
	}



}
