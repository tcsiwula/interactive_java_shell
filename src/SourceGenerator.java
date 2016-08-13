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
public class SourceGenerator
{

	public SourceGenerator()
	{

	}

	public String makeDeclaration(int classNumber, int extendsClass, String lineData)
	{
		StringBuilder declarationSource = new StringBuilder();
		String myNewClassName, myNewClassNameExtends;
		myNewClassName = "public class Interp_" + classNumber;
		myNewClassNameExtends = "Interp_" + extendsClass;
		//declarationSource.append(System.lineSeparator());
		declarationSource.append(System.lineSeparator());
		declarationSource.append(System.lineSeparator());

		declarationSource.append("import java.io.*;" + System.lineSeparator());
		declarationSource.append("import java.util.*;" + System.lineSeparator());

		if (classNumber == 0)
		{
			declarationSource.append(myNewClassName + " {" + System.lineSeparator());
			declarationSource.append("    public static " + lineData + System.lineSeparator());
			declarationSource.append("    public static void exec() {" + System.lineSeparator());
			declarationSource.append("    }" + System.lineSeparator());
			declarationSource.append("}" + System.lineSeparator());
		}
		else
		{
			declarationSource.append(myNewClassName + " extends " + myNewClassNameExtends + " {" + System.lineSeparator());
			declarationSource.append("    public static " + lineData + System.lineSeparator());
			declarationSource.append("    public static void exec() {" + System.lineSeparator());
			declarationSource.append("    }" + System.lineSeparator());
			declarationSource.append("}" + System.lineSeparator());
		}

//		String source = declarationSource.toString();
//		declarationSource.setLength(0);
//		System.out.println("declaration source = ");
//		System.out.println();
//		System.out.println(source);
		return declarationSource.toString();
	}


	public String makeStatement(int classNumber, int extendsClass, String lineData)
	{
		StringBuilder statementSource = new StringBuilder();
		String myNewClassName, myNewClassNameExtends;

		myNewClassName = "public class Interp_" + classNumber;
		myNewClassNameExtends = "Interp_" + extendsClass;
		//statementSource.append(System.lineSeparator());
		statementSource.append(System.lineSeparator());
		statementSource.append(System.lineSeparator());
		statementSource.append("import java.io.*;" + System.lineSeparator());
		statementSource.append("import java.util.*;" + System.lineSeparator());

		if (classNumber == 0)
		{
			statementSource.append(myNewClassName + " {" + System.lineSeparator());
			statementSource.append("    public static void exec() {" + System.lineSeparator());
			statementSource.append("    ");
			statementSource.append("    " + lineData + System.lineSeparator());
			statementSource.append("    }" + System.lineSeparator());
			statementSource.append("}" + System.lineSeparator());
		}
		else
		{
			statementSource.append(myNewClassName + " extends " + myNewClassNameExtends + " {");
			statementSource.append(System.lineSeparator());
			statementSource.append(System.lineSeparator());
			statementSource.append(System.lineSeparator());

			statementSource.append("    public static void exec() {" + System.lineSeparator());
			statementSource.append("    ");
			statementSource.append("    " + lineData + System.lineSeparator());
			statementSource.append("    }" + System.lineSeparator());
			statementSource.append("}" + System.lineSeparator());
		}

//		String source = statementSource.toString();
//		statementSource.setLength(0);

		//		System.out.println("statementSource = ");
//		System.out.println();
//		System.out.println(source);
		return statementSource.toString();
	}
}
