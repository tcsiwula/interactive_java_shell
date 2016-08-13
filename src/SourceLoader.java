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
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

public class SourceLoader extends ClassLoader
{

	public SourceLoader(ClassLoader parent)
	{
		super(parent);
	}


	public void loadClass(int classNumber, String path) throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		String name = ("Interp_"+classNumber );
		System.out.println("loadClass - name  = "+name);
		System.out.println("loadClass - path  = "+path);
		URL tmp = new File(path).toURI().toURL();
		URLClassLoader loader = new URLClassLoader(new URL[]{tmp});
		Class<?> c2 = loader.loadClass(name);
		Method m2 = c2.getDeclaredMethod("exec");
		m2.invoke(null, null);
	}
}
