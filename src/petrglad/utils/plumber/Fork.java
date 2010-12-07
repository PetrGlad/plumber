package petrglad.utils.plumber;

import java.util.List;
import java.util.Observable;
import java.util.Vector;

/**
 * Sink that replicates a put call over number of clients.
 * <p>
 * You may also consider using {@link Observable} instead of this class.
 * 
 * @author Petr Gladkikh
 */
public class Fork<T> implements Sink<T>, Dispenser<T>
{
	private final List<Sink<T>>	listeners	= new Vector<Sink<T>>(3);

	@Override
	public void add(Sink<T> sink)
	{
		synchronized(listeners)
		{
			if(!listeners.contains(sink))
			{
				listeners.add(sink);
			}
		}
	}

	@Override
	public void remove(Sink<T> sink)
	{
		listeners.remove(sink);
	}

	/**
	 * Relays input value to every listener sink.
	 */
	@Override
	public void put(T value)
	{
		List<Sink<T>> lst = null;
		// Iterate over copy so listeners can be (un)subscribed during put.
		synchronized(listeners)
		{
			lst = new Vector<Sink<T>>(listeners);
		}
		for(Sink<T> listener : lst)
		{
			listener.put(value);
		}
	}
}
