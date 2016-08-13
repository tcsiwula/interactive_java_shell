/**
 * =============================================================================
 * Project:      =        tcsiwula-repl
 * Package:      =        cs345.repl
 * Created:      =        2/6/16
 * Author:       =        Tim Siwula <tcsiwula@usfca.edu>
 * University:   =        University of San Francisco
 * Class:        =        Computer Science 345: Programming Languages
 * Liscense:     =        GPLv2
 * Version:      =        0.001
 * ==============================================================================
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class SourceWriter
{
	ArrayList<File> files;
	String property = "java.io.tmpdir";
	String tempDirectory = System.getProperty(property);

	public SourceWriter() throws IOException
	{
		files = new ArrayList<File>();
	}

	public void writeFile(String sourceToWrite, int classNumber) throws IOException
	{
		String fileName = "Interp_" + classNumber + ".java";
		Files.write(Paths.get(tempDirectory + "/" + fileName), sourceToWrite.getBytes());
		File theFile = new File(tempDirectory + "/" + fileName);
		files.add(theFile);
	}

	public String getTempDirectory()
	{
		return tempDirectory;
	}

	public void printTempDirectory()
	{
		System.out.println("Files in your tempDirectory: ");
		File test = new File(tempDirectory);
		File[] listOfFiles = test.listFiles();

		for (File aFile : listOfFiles)
		{
			if (aFile.isFile())
			{
				if (aFile.toString().contains(".class") || aFile.toString().contains(".java"))
					System.out.println(aFile.getName());
			}
		}
	}

	public ArrayList<File> getAllSourceFiles()
	{
		return files;
	}

	public String getLastFile()
	{
		File newJavaFile = files.get((files.size() - 1));
		System.out.println("returning this file/string = " + newJavaFile);

		return (newJavaFile.toString());
	}
}
