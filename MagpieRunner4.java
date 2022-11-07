import java.util.Scanner;

/**
 * A simple class to run the Magpie class.
 * @author Laurie White
 * @version April 2012
 */
public class MagpieRunner4
{

	/**
	 * Create a Magpie, give it user input, and print its replies.
	 */
	public static void main(String[] args)
	{
		Magpie4 maggie = new Magpie4();

		int questionsAsked = 0;
		
		System.out.println (maggie.getGreeting());
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
		
		while (!statement.equals("Bye"))
		{
			System.out.println (maggie.getResponse(statement, questionsAsked));
			statement = in.nextLine();
			if (maggie.getResponse(statement, questionsAsked).contains("I don't feel like talking to you anymore")) {
				statement = "Bye";
				System.out.println("I don't feel like talking to you anymore");
			}
			questionsAsked += 1;
		}
	}

}
