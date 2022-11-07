/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Magpie4
{
	String[] keywords = {"favorite", "team", "player", "finals", "win"};

	String[] teams = {"Golden State Warriors", "Cleveland Cavaliers", "Los Angeles Lakers", "Los Angeles Clippers", "Milwaukee Bucks", "Dallas Mavericks"};

	String[] players = {"Stephen Curry", "Lebron James", "Ja Morant", "Luka Doncic", "Michael Jordan", "Kobe Bryant", "Lamelo Ball"};

	List<String> randomQuestions = new ArrayList<String>(Arrays.asList("Who's your favorite player?", "Who's your favorite team?", "Who do you think will win the finals this year?", "Who do you think is going to win MVP this year?"));
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello, let's talk. My name is Samarth 2.0 and I like basketball. I love the NBA as well.";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement, int questionsAsked)
	{	double n = Math.random();
		int stopTalking = (int)(n * 50);


		String response = "";
		Random rand = new Random();

		if (stopTalking == 1) {
			response = "I don't feel like talking to you anymore";
		}
		else if (statement.length() == 0)
		{
			if (questionsAsked >= 5) {
				response = "Let's talk about the NBA. " + randomQuestions.get(rand.nextInt(randomQuestions.size()));
			} else {
			response = "Say something, please.";
			}
			System.out.println(questionsAsked);
		} else if (findKeyword(statement, "favorite") >= 0) {
			if (findKeyword(statement, "player") >= 0) {
				response = "My favorite player is Stephen Curry";
			}
			else if (findKeyword(statement, "team") >= 0) {
				response = "My favorite team is the Golden State Warriors";
			}
		}
		else if (findKeyword(statement, "finals") >= 0) {
			response = "The Golden State Warriors won the 2022 NBA Championship";
			}
		else if (findKeyword(statement, "best") >= 0) {
			response = "The best team in the league right now is whoever your favorite team is, just kidding ur team sucks, Lakers are better !!!";
		} 
		else if (findKeyword(statement, "ball") >= 0) {
			response = "The ball used in the NBA is called a basketball!";
		} 
		else if (findKeyword(statement, "Most Improved") >= 0) {
			response = "Ja Morant won this award last year";
		}
		else if (findKeyword(statement, "Curry") >= 0){
			response = "Yea its chef curry with the pot, hitting three's like a dot";
		}
		else if (findKeyword(statement, "rap") >= 0) {
			response = "The best rapper in the NBA is Damian Lillard";
		}
		else if (findKeyword(statement, "DJ") >= 0) {
			response = "The best DJ in the NBA is Shaq";
		}
		
		else if (findKeyword(statement, "MVP") >= 0) {
			response = "Nikola Jokic won the MVP last year, I think Luka Doncic is going to win this year";
		} else if (findKeyword(statement, "top 5") >= 0 || findKeyword(statement, "top five") >= 0) {
			response = "My top 5 of all time and current players is Brian Scalabrine, Javale Mcgee, Zaza Pachulia, Dennis Schroder, and Russell Westbrook";
		}
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}
		else if (findKeyword(statement, "mother") >= 0
				|| findKeyword(statement, "father") >= 0
				|| findKeyword(statement, "sister") >= 0
				|| findKeyword(statement, "brother") >= 0)
		{
			if (questionsAsked >= 5) {
				response = "Let's talk about the NBA. " + randomQuestions.get(rand.nextInt(randomQuestions.size()));
			} else {
			response = "Tell me more about your family.";
			}
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			if (questionsAsked >= 5) {
				response = "Let's talk about the NBA. " + randomQuestions.get(rand.nextInt(randomQuestions.size()));

			} else {
			response = transformIWantToStatement(statement);
			}
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				if (questionsAsked >= 5) {
					double r = Math.random();
					int randomChoice = (int)(r * 3);
				
					response = rewriteClarification(statement);
					if (randomChoice == 0) {
						response = "Let's talk about the NBA. " + randomQuestions.get(rand.nextInt(randomQuestions.size()));
					} else if (randomChoice == 1) {
						response = getClarification();
					} else if (randomChoice == 2) {
						response = rewriteClarification(statement);
					}
				} else {
				response = getRandomResponse();
				}
			}
		}
		return response;
	}

	private String getClarification() {
		return "Sorry I didn't quite understand. Can you reiterate your question?";
	}

	private String rewriteClarification(String statement) {
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}

		String lastWord = statement.substring(statement.lastIndexOf(" ")+1);

		return "What do you mean by " + lastWord + "?";
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "Hmmm.";
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse == 3)
		{
			response = "You don't say.";
		}

		return response;
	}

}
