package driver;

import java.io.IOException;
import java.util.Scanner;

import machine.Computation;

public class Driver {
public static Scanner scanner;
public static String input = null;
public static Computation computation = new Computation(80);

	public static String getInput() {
	return input;
}
public static void setInput(String input) {
	Driver.input = input;
}
	public static void main(String[] args){
		
		scanner = new Scanner(System.in);
		while(true){
			System.out.println("Select from the following.");
			System.out.println("[a]=Buy");
			System.out.println("[q]=Quit");
			System.out.println("[g]=Get");
			setInput(scanner.nextLine());
			
			if(getInput().length() > 0 && getInput().toCharArray()[0] == 'a')
			{
				scanner.reset();
				System.out.println("Enter expression:");
				setInput(scanner.nextLine());
				computation.getSetUpMachine().setInput(getInput());
				try {
					computation.enumerate();
					computation.execute();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}else if(getInput().length() > 0 && getInput().toCharArray()[0] == 'g'){
				System.out.println(computation.getVendingNumber());
			}
			else if(getInput().length() > 0 && getInput().toCharArray()[0] == 'q')
			{
				System.exit(0);

			}else{
				System.err.println("option " +" does not exist.");
			}
		
			scanner.reset();
		}
	}
}
