import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Exercise1 {
	public static void main(String[] args) {
		boolean option3 = false;
		HashMap<String, String> map = new HashMap<>();

		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			switch (s) {
			case "-o":
			case "-t":
				if (i + 1 >= args.length || args[i + 1].startsWith("-")) {
					System.err.printf("%s option requires an argument.\n", s);
					System.exit(1);
				}

				if (map.putIfAbsent(s, args[i + 1]) != null) {
					System.err.printf("%s option provided twice.\n", s);
					System.exit(1);
				}
				i += 1;
				break;
			case "-h":
				option3 = true;
				break;
			default:
				System.err.printf("Unrecognized command line argument: %s\n", args[i]);
				break;
			}
		}

		System.out.println("Standard input:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			String s;
			while ((s = in.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Command line arguments:");
		if (map.containsKey("-o")) {
			System.out.printf("option 1: %s\n", map.get("-o"));
		}

		if (map.containsKey("-t")) {
			System.out.printf("option 2: %s\n", map.get("-t"));
		}

		if (option3) {
			System.out.println("option 3");
		}
	}
}
