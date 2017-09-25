package warAndPeace;


public class SnippetCommaCounter implements Runnable {
	
	private  int counter;
	private String snippet;

	public int getCounter() {
		return counter;
	}

	public SnippetCommaCounter(String snippet) {
		this.snippet=snippet;
	}

	@Override
	public void run() {
		counter = WarPeace.countCommasLoop(snippet, counter);
//		System.out.println(counter);
	}

}