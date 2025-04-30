
abstract class Shape2 {
	private double height;
	private double width;
	
	Shape2(){
		height = 0.0;
		width = 0.0;
	}
	Shape2(double height, double width){
		this.height = height;
		this.width = width;
	}
	double getHeight(){
		return height;
	}
	double getWidth(){
		return width;
	}
	abstract double getArea();
}
//Abstract classes have some methods with code and others that are specified by subclasses. They can only be extended