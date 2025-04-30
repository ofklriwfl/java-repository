package package1;

interface Interface2 extends Interface1{
	//int getValue(); //Just because one interface extends another does not mean that the new one takes methods from the first one
	int getNewValue();
}
