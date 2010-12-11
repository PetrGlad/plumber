package petrglad.utils.plumber.example;

import petrglad.utils.plumber.Fork;
import petrglad.utils.plumber.Sink;

public class Example {
	public static class Button {
		private final Fork<Button> pushSource = new Fork<Button>();

		public void push() {
			pushSource.put(this);
		}

		public Fork<Button> getPushSource() {
			return pushSource;
		}
	}

	public static class Engine implements Sink<Button> {
		@Override
		public void put(Button value) {
			System.out.println("Rrrrrrrrrrrr! engine " + hashCode() + " activated by button "
					+ value.hashCode());
		}
	}

	public static void main(String[] args) {
		final Button b = new Button();
		final Engine e1 = new Engine();
		final Engine e2 = new Engine();
		b.getPushSource().add(e1);
		b.getPushSource().add(e2);

		b.push();

		b.getPushSource().remove(e2);

		b.push();
	}
}
