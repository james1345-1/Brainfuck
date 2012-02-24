package org.mithos.brainfuck;

public class CompileToC {
	
	public static String compile(String s) {

		StringBuilder $C = new StringBuilder(
				"#include <stdio.h>\n" +
				"int main(void)\n{\n\n" +
				"char array[30000];\nchar *ptr=array\n\n");
		for(char c : s.toCharArray()){
			switch(c){
			case '>':$C.append("++ptr;\n"); break;
			case '<':$C.append("--ptr;\n"); break;
			case '+':$C.append("++*ptr;\n"); break;
			case '-':$C.append("--*ptr;\n"); break;
			case '.':$C.append("putchar(*ptr);\n"); break;
			case ',':$C.append("*ptr=getchar();\n"); break;
			case '[':$C.append("while (*ptr)\n{\n");break;
			case ']': $C.append("}\n");break;
			}
		}
		
		$C.append("}\n");
		
		return $C.toString();
	}

}
