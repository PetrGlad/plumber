package petrglad.utils.plumber.example;

import java.util.HashSet;
import java.util.Set;

import petrglad.utils.plumber.Fork;
import petrglad.utils.plumber.Sink;

public class ChangesNotify {
	public static class Event<T> {
		public enum Type {
			UPDATE, REMOVE
		}

		final T value;
		final Type type;

		public Event(T value, Type type) {
			this.value = value;
			this.type = type;
		}
	};

	public static class Bag<T> {
		private final Fork<Event<T>> changeSource = new Fork<Event<T>>();

		public void add(T element) {
			changeSource.put(new Event<T>(element, Event.Type.UPDATE));
		}

		public void remove(T element) {
			changeSource.put(new Event<T>(element, Event.Type.REMOVE));
		}

		public Fork<Event<T>> getChangeSource() {
			return changeSource;
		}
	}

	public static class BagClone<T> {
		public final Set<T> items = new HashSet<T>();
		
		private final Sink<Event<T>> updateSink = new Sink<Event<T>>() {
			@Override
			public void put(Event<T> event) {
				switch (event.type) {
				case UPDATE:
					items.add(event.value);
					break;
				case REMOVE:
					items.remove(event.value);
					break;					
				}
			}
		};

		public Sink<Event<T>> getUpdateSink() {
			return updateSink;
		}
	}

	public static void main(String[] args) {
		Bag<String> bag = new Bag<String>();
		BagClone<String> clone = new BagClone<String>();		
		Sink<Event<String>> log = new Sink<Event<String>>() {
			@Override
			public void put(Event<String> event) {
				System.out.println("Event " + event.type + " \"" + event.value + "\"");
			}
		};		
		
		bag.getChangeSource().add(clone.getUpdateSink());
		bag.getChangeSource().add(log);
		
		bag.add("hell");
		bag.add("wold");
		bag.remove("hell");
		bag.add("hello");
		
		System.out.println("Items " + clone.items);
	}
}
