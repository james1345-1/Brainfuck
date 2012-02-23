package org.mithos.brainfuck;

import java.io.*;

import static java.lang.System.out;

/**
 * A Brainfuck interpreter class.
 *
 * When run as main, this class will accept either a string or filename and interpret it.
 * This class implements Runnable, so may be instansiated within other programs, and, if desired
 *  run in its own thread.
 */
public final class Brainfuck implements Runnable{

	// Instance info.
	// Instance of Brainfuck interpreter, actually handles interpreting.
	
	/** The array that acts as the interpreter's virtual memory */
	private char[] mem;
	/** The memory pointer */
	private int mp;
	
	/** The string containing the comands to be executed */
	private final char[] string;
	
	/** The instruction pointer */
	private int ip = 0;
	
	private final int EOF; //End Of File
	
	/**
	 * Create the Brainfuck VM and give it the string to be interpreted.
	 * @param s The string to be interpreted.
	 */
	public Brainfuck(String s){
		
		mem = new char[30000];
		mp = 0;
		string = s.toCharArray();
		EOF = string.length;
		
	}
	
	/**
	 * Run the interpreter with its given string
	 */
	@Override
	public void run(){
		while(ip < EOF){
			
			// Get the current command
			char c = string[ip];
			
			// Act based on the current command and the brainfuck spec
			switch(c){
			case '>':mp++; break;
			case '<':mp--; break;
			case '+':mem[mp]++; break;
			case '-':mem[mp]--; break;
			case '.':out.print((mem[mp])); break;
			case ',':try {
					mem[mp] = (char)System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				} break;
			case '[': if(mem[mp] == 0){
				int count = 1;
				while(count > 0){
					ip++;
					if(string[ip]=='[') count++;
					if(string[ip]==']') count--;
				}
			}break;
			
			case ']': if(mem[mp] != 0){
				int count = 1;
				while(count > 0){
					ip--;
					if(string[ip]=='[') count--;
					if(string[ip]==']') count++;
				}
			}break;
			}
			
			// increment instruction pointer
			ip++;
			
		}
	}
	
	// Static stuff - boilerplate code and file reading
	
	public static final int EXIT_SUCCESS = 1;
	public static final int EXIT_FAILURE = -1;
	
	/**
	 * Set up a single instance of the brainfuck interpreter, and run it, with the given string or file.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {	
		String s = "";
		
		// Test that there are exactly two arguments
		if(args.length!=2){
			usage();
		}
		
		// Assign s
		if(args[0].equals("-f")){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(args[1])));
				String line = "";
				StringBuilder sb = new StringBuilder();
				
				// Read file line by line. Removes newlines, but that's okay as brainfuck ignores them anyway.
				while ((line = reader.readLine())!=null){
					sb.append(line);
				}
				s = sb.toString();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		else if (args[0].equals("-i")){
			s = args[1];
		}
		else {
			usage();
		}
		
		// Start the interpreter
		(new Brainfuck(s)).run();
		
		// Exit
		System.exit(EXIT_SUCCESS);

	}
	
	/**
	 * Called when incorrect parameters are used.
	 */
	public static void usage(){
		out.printf("Usage:\n\tbrainfuck -f <filename>\n\tbrainfuck -i <string>\n");
		out.printf("For help:\n\tbrainfuck -h\n\tbrainfuck --help\n");
		System.exit(EXIT_FAILURE);
	}

}
