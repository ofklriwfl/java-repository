
class Rectangle2 extends Shape2 {
	Rectangle2(){
		super();
	}
	Rectangle2(double height, double width){
		super(height, width);
	}
	double getArea() {
		return getHeight() * getWidth();
	}
	
}
