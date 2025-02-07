package TechM;

public class Final_keyword {
	public   void brake() {
		System.out.println("break");
	}
	public static void main(String[] args) {
		
		String name="sanjana";
		final int age;
		age=20;//we can either assign the value to the variable while declaring or later but we can't not reassign or override the value of the constant
		//age=21;
		System.out.println(name+" is"+" "+age+" years");
		Final_keyword v1=new Final_keyword();
		v1.brake();
	}

}
