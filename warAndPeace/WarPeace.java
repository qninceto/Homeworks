package warAndPeace;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class WarPeace {
	private static final int NUMBER_OF_DEVIDED_PARTS = 10;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// read from the file and save in a string:
		File warandPeace = new File("part3" + File.separator + "warandpeace.txt");
		String text = "";
		text = readTheFileAndSaveInString(warandPeace, text);

		// save the 5 snippets in a list:
		List<String> listOfSnippets = new ArrayList<>();
		for (int i = 1; i <= NUMBER_OF_DEVIDED_PARTS; i++) {
			listOfSnippets.add(returnSnippet(text, i));
		}

		// calculate commas without threads:
		long start = System.currentTimeMillis();
		 calculateCommasWithoutThreads(text,start);// ~32 sec

		// calculate the commas with 5 threads:
		long start2 = System.currentTimeMillis();
		 calculateCommasWithFiveThreads(text, listOfSnippets,start2);

		// calculate the commas with a fixedThreadPool:
		long start3 = System.currentTimeMillis();
		calculateCommasWithPool(listOfSnippets, start3);

	}

	static void calculateCommasWithPool(List<String> listOfSnippets, long start3)
			throws InterruptedException, ExecutionException {
		System.out.println("-------------------using a fixed size threadPool:");
		int counter3 = 0;
		//with future:
//		List<Future<Integer>> s = new ArrayList<Future<Integer>>();
		ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_DEVIDED_PARTS);
		//with completionservice:
		CompletionService<Integer> cs = new ExecutorCompletionService<>(threadPool);
		
		for (int i = 0; i < NUMBER_OF_DEVIDED_PARTS; i++) {
			//with future:
//			s.add(threadPool.submit(new SnippetCommaCounterFuture(listOfSnippets.get(i))));
			//with completionservice:
			cs.submit(new SnippetCommaCounterFuture(listOfSnippets.get(i)));
		}
		//with future:
//		for (Future<Integer> f : s) {
//			counter3 += f.get();
//		}
		//with completionservice:
		//for each loop????
		for(int i = 0; i < NUMBER_OF_DEVIDED_PARTS; i++) {
			counter3 += cs.take().get();
		}
		//does it matter if i shutdown cs or threadPool???
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("time elapsed for the pool: " + (System.currentTimeMillis() - start3));
		System.out.println("Number of the commas: " + counter3);
	}

	static void calculateCommasWithFiveThreads(String text, List<String> listOfSnippets, long start2) {

		List<Thread> tl = new ArrayList<>();
		List<SnippetCommaCounter> scs = new ArrayList<>();

		for (int i = 1; i <= NUMBER_OF_DEVIDED_PARTS; i++) {

			// take a snippet from the list:
			String snippet = listOfSnippets.get(i - 1);

			SnippetCommaCounter sc = new SnippetCommaCounter(snippet);
			scs.add(sc);
			Thread t = new Thread(sc);
			tl.add(t);
			t.start();

			// lars: create a thread inside SnippetCommaCounter, and a method with the name:
			// sc.countCommasAsync();
			// then call directlly that function and get the state of the object--->no list
			// needed!!!
			// Thread snippetThread = new Thread(this);
			// tl.add(snippetThread);
			// snippetThread.start();
		}

		for (Thread t : tl) {
			try {
				// lars:
				// sc.waitForCountFinish();
				t.join();
			} catch (InterruptedException e) {
				return;
			}
		}

		int counter2 = 0;
		for (SnippetCommaCounter s : scs) {
			counter2 += s.getCounter();
		}
		System.out.println("number of commas with 5 threads: " + counter2);
		System.out.println("time elapsed for 5 threads: " + (System.currentTimeMillis() - start2));

		System.out.println("---------------------------------------------------------");
	}

	static void calculateCommasWithoutThreads(String text, long start) {
		int counter = 0;

		counter = countCommasLoop(text, counter);
		System.out.println("time elapsed: " + (System.currentTimeMillis() - start));
		System.out.println("number of commas: " + counter);

		System.out.println("---------------------------------------------------------");
	}

	static int countCommasLoop(String text, int counter) {
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == ',') {
				counter++;
			}
		}
		return counter;
	}

	static String returnSnippet(String text, int i) {
		StringBuilder snippet = new StringBuilder(text.substring((text.length() / NUMBER_OF_DEVIDED_PARTS) * (i - 1),
				(text.length() / NUMBER_OF_DEVIDED_PARTS) * i));
		if (text.length() % NUMBER_OF_DEVIDED_PARTS != 0 && i == NUMBER_OF_DEVIDED_PARTS) {
			snippet.append(text.substring(((text.length() / NUMBER_OF_DEVIDED_PARTS) * NUMBER_OF_DEVIDED_PARTS),
					text.length()));
		}
		return snippet.toString();
	}

	static String readTheFileAndSaveInString(File warandPeace, String text) {
		try (InputStream warandp = new FileInputStream(warandPeace);) {
			// URL("https://www.gutenberg.org/files/2600/2600-0.txt").openStream();
			int x;
			byte[] data = new byte[(int) warandPeace.length()];
			int index = 0;
			do {
				x = warandp.read();

				if (x != -1) {
					data[index++] = (byte) x;
				}
			} while (x != -1);

			text = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
}