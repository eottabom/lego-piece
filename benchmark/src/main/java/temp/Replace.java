package temp;

public class Replace {

	public static String replaceCharacters(String input) {
		return input.replace("<", "&#60;")
				.replace(">", "&#62;")
				.replace("?", "&#63;");
	}

	public static void main(String[] args) {
		String input = """
			public class Example {
				private final List<Integer> numbers = new ArrayList<>();
				public Example() {
					numbers.add(1);
					numbers.add(2);
				}
				public List<Integer> getNumbers() {
					return numbers;
				}
			}
	""";

		String output = replaceCharacters(input);

		System.out.println(output);
	}
}
