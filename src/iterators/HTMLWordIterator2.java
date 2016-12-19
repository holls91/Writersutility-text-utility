package iterators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HTMLWordIterator2 extends WordIterator {

	private int minLength;
	private Pattern pattern;
	private Matcher matcher;
	private Fragment currentFragment;
	private int findIndex;

	public HTMLWordIterator2(FragmentIterator fragmentIterator, int minLength) {
		super(fragmentIterator, minLength);
		this.minLength = minLength;
		//FIXME Non prende parole tipo éléfant
		pattern = Pattern.compile("([A-Za-z�-�]{"+minLength+",}(?:[A-Za-z�-�]|(&#(?:19[2-9]|2[0-4][0-9]|25[0-5]);)))");
		pattern.matcher(text);
	}

	private Word word = new Word("", 0);

	@Override
	public boolean hasNext() {
		if(currentFragment==null){
			if(!fragmentIterator.hasNext())
				return false;
			else {
				currentFragment = fragmentIterator.next();
				matcher = pattern.matcher(currentFragment.getText());
				findIndex = 0;
				System.out.println(currentFragment.getText());
			}
		}
		if(matcher.find(findIndex)) {
			if (!(matcher.group().length() == 0
					|| matcher.group(2) == null && matcher.group(1).length() < minLength
					|| matcher.group(2) != null
					&& (matcher.group(1).length() - matcher.group(2).length()) + 1 < minLength)) {
				word = new Word(matcher.group(), matcher.start() + currentFragment.getIndex());
				findIndex = matcher.end();
				return true;
			}
		}
		else {
			currentFragment = null;
			return hasNext();
		}
		return false;
	}

	@Override
	public Word next() {
		return word;
	}

//	public static void main(String[] args) {
//		String text = "<html><body><div>FILONE FANTASY:</div><div>syria &#232; un cavaliere della luce di categoria bianca, nonch&#233; principessa del pianeta Vega, che viene mandata in missione sulla Terra alla ricerca di Lucifero. Chi &#232; Lucifero? Lucifero &#232; il leggendario guerriero &#34;portatore di luce&#34; che dovr&#224; aiutare tutto l'ordine dei cavalieri a salvare l'universo dai nemici che vogliono sottomettere tutti i pianeti in cui vi &#232; vita.</div><div>Inizier&#224; quindi la sua avventura sulla Terra. Proprio quando sta iniziando ad ambientarsi, cominciano per&#242; i guai, perch&#233; subentreranno queste forze malvagie che nessuno si aspettava arrivassero sulla Terra, poich&#233; si riteneva che la Terra, essendo un pianeta spiritualmente e culturalmente sopito (rispetto agli altri pianeti, in cui gli abitanti sono in grado di interagire con la natura ed usare i poteri della mente). Verr&#224; quindi colta impreparata e sar&#224; per lei complicato affrontare questi demoni che si impossessano delle persone per nutrirsi dei loro sentimenti negativi. Il fatto che siano arrivati dei demoni sulla Terra, implica che i nemici hanno scoperto che qualcuno &#232; stato mandato in missione su quel pianeta e stanno cercando di fermarlo. Ergo, questi demoni fanno parte dell'esercito del male. In una di queste battaglie con i demoni, uno dei personaggi in rilievo perder&#224; la vita a causa di un fallimento di Lady Syria e Lord Vega (che raggiunger&#224; Syria sulla Terra in seguito per darle aiuto). Il demone infatti che possedeva in quel momento quel personaggio, prima che venga ucciso entra dentro Lord Vega, quindi Syria &#232; disperata perch&#233; deve assolutamente salvarlo. In seguito, uno degli antagonisti principali, che si fa chiamare Lady At&#233;ra, andr&#224; sulla Terra a spiare cosa effettivamente sta accadendo e l&#236; incontra Lady Syria che colpir&#224; quasi a morte, ma senza ucciderla, perch&#233; la chiama &#34;sorella&#34;. Lady At&#233;ra torna nel suo buco nero insieme a Lord Thanatos e l&#236; cominciano a pianificare la battaglia finale, perch&#233; At&#233;ra e Thanatos sono coloro che sanno tutta la verit&#224; su Syria. Infatti Syria ha addosso una maledizione che consiste nel dividere la sua vita con una tigre. Di giorno quindi &#160;&#232; umana e ogni notte &#34;muore&#34; tra atroci dolori per trasformarsi in tigre. Alla fine di tutto, si sopraggiunger&#224; a questa battaglia finale in cui verr&#224; fuori Lucifero. La battaglia devo ancora dettagliarla, ma ovviamente i cattivi periranno XD</div></body></html>";
//		WordIterator wi = new HTMLWordIterator2(new HTMLFragmentIterator(text));
//		Iterator<Word> it = wi.iterator();
//		while (it.hasNext()) {
//			Word word = it.next();
//			if(word != null)
//				System.out.println(word.getParola() +"\t"+word.getPosition()+ "\n");
//		}
//	}

}
