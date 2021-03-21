package java_dig_dsg_intro;
import java.util.regex.*;
import java.io.*;

public class string_wr {
		String wr;										// входная строка
		String unwr;									// распакованная строка	
		String filename="string_wr.txt";				// имя файла
		
		// конструктор
		public string_wr(){
			try {
				File f = new File(filename);
		        if(f.exists()) {
		            FileReader fr = new FileReader(f);
		            BufferedReader reader = new BufferedReader(fr);
		            String line = new String();
		            line = reader.readLine();
		            wr="";
		            while (line != null) {
		                wr += line;
		                line = reader.readLine();
		                
		            }
		            reader.close();
		        }
		        else {
		        	f.createNewFile();
		        	this.wr = "3[xyz4[xy]]z"; // default
		        	try(FileOutputStream fos=new FileOutputStream(filename)){
        				byte[] buffer = this.wr.getBytes();
            			fos.write(buffer, 0, buffer.length);
        			}
        			catch(IOException ex){
        				System.out.println(ex.getMessage());
        			}
		        }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		// проверка строки на валидность
		public boolean checkthestring() {
			boolean valid=true;
			int j=0;
			for(j=0; j<this.unwr.length();j++){
				char c = this.unwr.charAt(j);
				if(!Character.isAlphabetic(c) & c!=' '){
					valid = false;
				}
	    	 }
	    	 if(valid)
	    		 return true;
	    	 else
	    		 return false;
		}
		// шаблон для поиска в строке
		static final Pattern mypattern = Pattern.compile("(\\d+)\\[([^\\d\\[\\]]*)\\]");
		
		// функция поиска в строке по шаблону
		static String findapattern(String mystring) {
			StringBuilder build = new StringBuilder();
		    Matcher match = mypattern.matcher(mystring);
		    while (match.find())
		        match.appendReplacement(build, match.group(2).repeat(Integer.parseInt(match.group(1))));
		    match.appendTail(build);
		    return build.toString();
		}
		// функция распаковки строки
		public void string_unwr() {
			String temp = this.wr;
			this.unwr = findapattern(temp);
			while(!this.unwr.equals(temp)) {
		        temp = this.unwr;
		        this.unwr = findapattern(temp);
			}
			if(!this.checkthestring()) {
				this.unwr = "!!!! Входная строка не валидна !!!!";
			}
		    try(FileOutputStream foss=new FileOutputStream("string_unwr.txt")){
				byte[] buffer = this.unwr.getBytes();
    			foss.write(buffer, 0, buffer.length);
			}
		    catch(IOException ex){
				System.out.println(ex.getMessage());
			}
		}
		
		public static void main(String[] args) {
	        string_wr mystring = new string_wr();
	        mystring.string_unwr();
		}
}
