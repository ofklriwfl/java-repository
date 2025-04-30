import package1.Class1;
//import package1.Class2;

public class Classes1 {

	public static void main(String[] args) {
		//If classes are public, they can be accessed from a different package without using the name of the package
		Class1 one = new Class1(); //Accessing a public class in a different package
		System.out.println(one.getValue());
		package1.Class2 two = new package1.Class2(); //Also accessing a public class in a different package
		System.out.println(two.getValue());
	}

}
