package employeesCollections;

public class Demo {
	public static void main(String[] args) {
		Company a = new Company(6);
		a.hire("pesho", "0888 365 875", 1500, 2);
		a.hire("misho", "0888 365 875", 1200, 2);
		a.hire("ana", "0888 365 875", 100, 2);
		a.hire("sali", "0888 365 875", 500, 3);
		a.hire("cv", "0888 365 875", 1500, 1);
		a.hire("xa", "0888 365 875", 1500, 6); 
		
		a.spravka();
	}
}
