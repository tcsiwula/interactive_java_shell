import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Driver
{
	// start of program
	public static void main(String[] args) throws IOException
	{
		exec(new InputStreamReader(System.in));	// System.in is line buffered
	}

	public static void exec(Reader r) throws IOException
	{
		BufferedReader stdin = new BufferedReader(r); // character stream, connected to console
		NestedReader reader = new NestedReader(stdin);
		SourceGenerator generator = new SourceGenerator();
		SourceWriter writer = new SourceWriter();
		SourceLoader loader = new SourceLoader(ClassLoader.getSystemClassLoader());
		Compiler timsCompiler = new Compiler();
		int classNumber = 0;

		//System.out.println("Enter your java code here: ");
		System.out.print("> ");
		boolean printFlag = false;
		boolean compiledDeclaration = false;
		boolean compiledStatement = false;

		do
		{
			String java = reader.getNestedString();

			if((reader.getLookaheadCharacter()) == '\n')
			{
				//System.out.println("java = " + java);
				if(java.contains("print") && java.contains(";"))
				{
					if (java.charAt(0) == 'p' && java.charAt(1) == 'r' && java.charAt(2) == 'i' && java.charAt(3) == 'n' && java.charAt(4) == 't')
					{
						java = java.replace("print", "System.out.println(");
						java = java.replace(";", ");");
					}

					//System.out.println("java = " + java);
				}
				//System.out.println();
				String declarationSource = new String();
				 declarationSource = generator.makeDeclaration(classNumber, (classNumber - 1), java);
				writer.writeFile(declarationSource, classNumber);
				//System.out.println("Trying - Compile as declaration.");
				//System.out.println();
//				System.out.println("declarationSource = ");
//				System.out.println();
//				System.out.println(declarationSource);

				if (timsCompiler.isDeclaration(writer.getAllSourceFiles(), writer.getTempDirectory()))
				{
					compiledDeclaration = true;
					//timsCompiler.compileDiskFile(writer.getAllSourceFiles(), writer.getTempDirectory());
					classNumber++;    // increment for each line processed from stdin.
				}
				else
				{
					//System.out.println("	Result - declaration compilation failed.");
					//System.out.println();

					String statementSource = new String();
					statementSource = generator.makeStatement(classNumber, (classNumber - 1), java);
//					System.out.println("statementSource = ");
//					System.out.println();
//					System.out.println(statementSource);

					writer.writeFile(statementSource, classNumber);
					//System.out.println("Trying - Compile as statement.");
					//timsCompiler.compileDiskFile(writer.getAllSourceFiles(), writer.getTempDirectory());

					if (timsCompiler.compileDiskFile(writer.getAllSourceFiles(), writer.getTempDirectory()))
					{
						compiledStatement = true;
						classNumber++;    // increment for each line processed from stdin.
					}
				}

//				if ((compiledDeclaration == false) && (compiledStatement == false))
//				{
//					timsCompiler.printErrors("either");
//				}

				if(compiledDeclaration || compiledStatement)
				{
					//System.out.println("	Result - is statement.");
					String name = ("Interp_"+(classNumber-1)  );
					//System.out.println("loadClass - name  = "+name);
					//System.out.println("loadClass - path  = "+writer.getTempDirectory());
					URL tmp = new File(writer.getTempDirectory()).toURI().toURL();
					URLClassLoader loader2 = new URLClassLoader(new URL[]{tmp});
					Class<?> c2 = null;
					try
					{
						c2 = loader2.loadClass(name);
					} catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					}
					Method m2 = null;
					try
					{
						m2 = c2.getDeclaredMethod("exec");
					} catch (NoSuchMethodException e)
					{
						e.printStackTrace();
					}
					try
					{
						m2.invoke(null, null);
					} catch (IllegalAccessException e)
					{
						e.printStackTrace();
					} catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}

				compiledDeclaration = false;
				compiledStatement = false;
				System.out.print("> ");

				//writer.printTempDirectory();
			}
//			reader.consume();

		}
		while (stdin.readLine() != null);

	}
}