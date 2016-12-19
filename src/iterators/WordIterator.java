package iterators;

import java.util.Iterator;


public abstract class WordIterator implements Iterable<Word>, Iterator<Word> {
	
	protected String text = "";
	protected FragmentIterator fragmentIterator;
	
	public WordIterator(FragmentIterator fragmentIterator, int minLength){
		this.fragmentIterator = fragmentIterator;
	}

	public Iterator<Word> iterator() {
		return this;
	}
}
