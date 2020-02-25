package com.vmware.javatoproto.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import com.lloyd.JavaToProto.JavaToProto;

public class GenerateProto 
{

    public static void main( String[] args )
    {
    	if (args.length != 2) {
    		System.out.println("Kindly provide location of file containing classes and output directory");
    		System.exit(-1);
    	}
        String file_path_cwith_class_names = args[0];
        String output_directory = args[1];
        
        File file =  new File(file_path_cwith_class_names);
	try {
	        Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String className = sc.nextLine();
			if ("".equals(className) || className.equals(null)){
				continue;
			}
			Class clazz;
			try {
				clazz = Class.forName(className);
			} catch (Exception e) {
				System.out.println("Could not load class. Make Sure it is in the classpath!! \n" + className);
				e.printStackTrace();
				return;
			}
	      	      	//System.out.println(className);
			JavaToProto jpt = new JavaToProto(clazz);
			String protoFile = null;
			try {
				protoFile = jpt.toString();
			}catch(Exception ex){
				System.out.println(className);
				System.out.println(ex.getMessage());
				//ex.printStackTrace();
				continue;
                        }
				
			//Write to File
			try 
			{
				File f = new File(output_directory + "/" + className + ".proto");
					FileWriter fw = new FileWriter(f);
					BufferedWriter out = new BufferedWriter(fw);
					out.write(protoFile);
					out.flush();
					out.close();
			} catch (Exception e) {
				System.out.println("Got Exception while Writing to File - See Console for File Contents");
				System.out.println(protoFile);
				e.printStackTrace();
				}
			}
			sc.close();
		} catch (Exception e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		}
    }
}
