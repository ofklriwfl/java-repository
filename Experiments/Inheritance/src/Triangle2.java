
class Triangle2 extends Shape2 {
	Triangle2(){
		super();
	}
	Triangle2(double height, double width){
		super(height, width);
	}
	double getArea() {
		return (.5 * getHeight() * getWidth());
	}
}
