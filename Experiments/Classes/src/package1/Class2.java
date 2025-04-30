package package1;

public class Class2 extends Class1 {  
	//Class2 not required to implement methods from Class1 and would not be required to implement methods from Interface1 via Class1 or if Class2 specifically implemented Interface1. 
	//However, Class2 would be required to implement methods from Interface2 and Interface3 if they were specifically implemented.
	//This is because Class1 does not implement Interface2 or Interface3.
	private int value;
	
	public Class2(){
		this.value = 2;
	}
	Class2(int value){
		this.value = value;
	}
	
}
