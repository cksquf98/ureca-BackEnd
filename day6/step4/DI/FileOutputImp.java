package com.ureca.step4.DI;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputImp implements FileOutput {
	
	/*
	         <write>            <read>
	 byte    outputStream       InputStream
	 ----------------------------------------
	 char    Writer             Reader
	 
	 */

	@Override
	public void output(String str) {
		FileWriter fw;
		try {
			fw = new FileWriter("helloTest.txt");
			
			//파일에 str을 출력하기
			fw.write(str);
			
			fw.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
