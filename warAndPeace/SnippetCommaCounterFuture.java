package warAndPeace;

import java.util.concurrent.Callable;

public class SnippetCommaCounterFuture implements Callable<Integer> {
	private  int counter;
	private String snippet;
	
	public SnippetCommaCounterFuture(String snippet) {
		this.snippet = snippet;
	}

	@Override
	public Integer call() throws Exception {
		counter = WarPeace.countCommasLoop(snippet, counter);
//		System.out.println(counter);
		return counter;
	}

}
