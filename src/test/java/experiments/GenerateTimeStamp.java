package experiments;

import java.util.Date;

public class GenerateTimeStamp {
	
	public static void main(String[] args) {
		
		Date date=new Date();
		System.out.println(date.toString().replace(" ", "_").replace(":","_"));
	}

}
