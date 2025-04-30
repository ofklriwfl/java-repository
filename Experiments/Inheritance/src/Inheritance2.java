
public class Inheritance2 {

	public static void main(String[] args) {
		//Shape2 shape = new Shape2(); //An object of an abstract class cannot be instantiated
		Shape2 triangle = new Triangle2(3, 2);
		System.out.println(triangle.getClass()); //prints "Triangle2" even though Shape2 is used as part of the initialization
		Shape2[] shapes = new Shape2[2];
		System.out.println(shapes.getClass()); //prints "[LShape2", shows that an array of an abstract class can be instantiated
		shapes[0] = new Rectangle2(1, 1);
		System.out.println(shapes[0].getClass()); //prints "Rectangle2", shows that each elements of Shape2 array can be of a different type
		shapes[1] = new Triangle2(1, 1);
		Rectangle2 square = new Rectangle2(4, 4);
		Triangle2 angles = new Triangle2(6, 5);
		System.out.println(triangle.getArea());
		System.out.println(angles.getArea());
		Shape2 cube = (Shape2) new Rectangle2(); //prints "Rectangle2", cast to Shape2 does not appear to work
		System.out.println(cube.getClass());
		Shape2 box = new Rectangle2();
		Rectangle2 block = new Rectangle2();
		Shape2 rectangle = (Shape2) block;
		//Rectangle block = (Rectangle) new Shape();
	}

}
