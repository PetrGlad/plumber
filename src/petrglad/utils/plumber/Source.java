package petrglad.utils.plumber;

/**
 * Any zero-arg function (getter, factory, etc.).
 * 
 * @author Petr Gladkikh
 */
public interface Source<T>
{
	T get();
}
