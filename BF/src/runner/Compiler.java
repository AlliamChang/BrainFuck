package runner;

import java.util.*;

public class Compiler {
	
	private int dataPointer;
	
	private ArrayList<Character> data;
	
	private ArrayList<Integer> cirStart;
	
	private int stackPointer;
	
	private StringBuilder result;
	
	private char[] input;
	
	private int inputCounter;
	
	private void init(){
		dataPointer = 0;
		cirStart = new ArrayList<Integer>();
		stackPointer = 0;
		inputCounter = 0;
		data = new ArrayList<Character>();
		result = new StringBuilder();
		data.add('\0');
	}
	
	public String getResult(){
		return result.toString();
	}
	
	protected static class Token{
		public final static char NEXT = '>';
		public final static char LAST = '<';
		public final static char PLUS = '+';
		public final static char MINUS = '-';
		public final static char INPUT = ',';
		public final static char OUTPUT = '.';
		public final static char WHILE_RIGHT = ']';
		public final static char WHILE_LEFT = '[';
	}
	
	public Compiler(String str,String in){
		this.input = in.toCharArray();
		init();
		interpret(str.toCharArray());
	}
	
	public Compiler(String str){
		init();
		interpret(str.toCharArray());
	}
	
	public void interpret(char[] code){
		
		for(int i = 0; i < code.length; i ++){
			switch(code[i]){
			case ' ':
			case '\r':	
			case '\n':break;	
			case Token.NEXT: 
				dataPointer ++;
				if(data.size() < dataPointer + 1){
					data.add('\0');
				}
				break;
			case Token.LAST:
				dataPointer --;
				if(dataPointer < 0){
					System.out.println("dataPointer Error");
				}
				break;
			case Token.PLUS:
				data.set(dataPointer, (char)( data.get(dataPointer) + 1));
				break;
			case Token.MINUS:
				data.set(dataPointer, (char)( data.get(dataPointer) - 1));
				break;
			case Token.OUTPUT:	
				result.append(data.get(dataPointer));
				break;
			case Token.INPUT:
				if(inputCounter < input.length){
					data.set(dataPointer, input[inputCounter]);
					inputCounter ++;
				}else{
					System.out.println("Input Error");
				}
				break;
			case Token.WHILE_LEFT:
				cirStart.add(i);
				stackPointer ++;
				break;
			case Token.WHILE_RIGHT:	
				if(data.get(dataPointer) != 0){
					i = cirStart.get(stackPointer - 1);
				}else{
					stackPointer --;
					cirStart.remove(stackPointer);
					}
				break;
			default:
				System.out.println("Text Error");
				break;
			}
		}
	}
	
	
}
