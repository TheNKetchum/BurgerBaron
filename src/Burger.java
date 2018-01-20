/**
 * Burger class that creates a burger based on a customers order. 
 * 
 * @author Benjamin Yuen, Alex Bledsoe
 * @version Jan 16, 2018
 */
public class Burger {
	 
	/** The top half of the burger, usually a bun and veggies. */
 	private MyStack<String> myBurgerTop;
 	
 	/** The bottom half of the burger, usually a bun, patties and cheese. */
 	private MyStack<String> myBurgerBottom;
 	
 	/** Keeps track of the number of patties on the burger. */
 	private int myPattyCount;

	/**
	 * A constructor that initializes a Burger with only a bun and
	 * patty if theWorks is false and a Baron Burger if theWorks is true.
	 * 
	 * @param theWorks determines if the Burger is a Baron Burger or not.
	 */
	public Burger (boolean theWorks) {
		myBurgerTop = new MyStack<>();
		myBurgerBottom = new MyStack<>();
			
		if (theWorks) {
			makeBaron();
		} else {
			makeBasic();
		}
		
		myPattyCount++;
	}

	/**
	 * Changes all patties in the Burger to the pattyType.
	 * 
	 * @param thePattyType the patty type that will change all patties 
	 */
	public void changePatties(String thePattyType) {
		String patty;
		if ("Chicken".endsWith(thePattyType)) {
			patty = Ingredients.CHICKEN_PATTY;
		} else {
			patty = Ingredients.VEGGIE_PATTY;
		}
		MyStack<String> cheeseHolder = new MyStack<>();
		int currentPattyCount = myPattyCount;
		while (currentPattyCount > 0) {
			if (myBurgerBottom.peek() != Ingredients.BEEF_PATTY) {
				cheeseHolder.push(myBurgerBottom.pop());
			} else {
				myBurgerBottom.pop();
				currentPattyCount--;
			}
		}
		myBurgerBottom.push(patty);
		currentPattyCount++;
		while (!cheeseHolder.isEmpty()) {
			myBurgerBottom.push(cheeseHolder.pop());
		}
		while (currentPattyCount < myPattyCount) {
			myBurgerBottom.push(cheeseHolder.pop());
			currentPattyCount++;
		}
	}
	
	/**
	 * Adds one patty to the Burger up to the maximum of 3. 
	 */
	public void addPatty() {
		myBurgerBottom.push(Ingredients.BEEF_PATTY);
		myPattyCount++;
	}

	/**
	 * Removes one patty from the Burger down to the minimum of 1. 
	 */
	public void removePatty() {
		myBurgerBottom.pop();
		myPattyCount--;
	}
	
	/**
	 * Adds all items of the type to the Burger in the proper locations. 
	 * 
	 * @param theType item that will be added to the Burger
	 */
	public void addCategory(String theType) {
		MyStack<String> catHolder = new MyStack<>();
		//MyStack<String> holder2 = new MyStack<>();
		if (theType == "Cheese") {
			addCheese(catHolder, Ingredients.CHEDDAR, Ingredients.MOZZARELLA, Ingredients.PEPPERJACK);
			
		} else if (theType == "Sauce") {
			//Puts mayonnaise and baron sauce on top bun
			addSauce(myBurgerTop, catHolder, Ingredients.MAYONNAISE, Ingredients.BARON_SAUCE);
			
			//Puts ketchup and mustard on bottom bun
			addSauce(myBurgerBottom, catHolder, Ingredients.KETCHUP, Ingredients.MUSTARD);
			
		} else if (theType == "Veggies") {
			//Puts lettuce, tomato, and onions on top bun
			addVeggies(myBurgerTop, catHolder, Ingredients.MAYONNAISE, Ingredients.BARON_SAUCE, 
					Ingredients.LETTUCE, Ingredients.TOMATO, Ingredients.ONIONS);
			
			//Puts mushrooms on bottom bun
			addVeggies(myBurgerBottom, catHolder, Ingredients.KETCHUP, Ingredients.MUSTARD, 
					Ingredients.MUSHROOMS, "", "");
		}
	}
		
	/**
	 * Removes all items of the type from the Burger. 
	 * 
	 * @param theType item that will be remove to the Burger
	 */
	public void removeCategory(String theType) {
		
	}
	
	/**
	 * Adds the ingredient type to the Burger in the proper location.
	 * 
	 * @param theType ingredient that will be added to the Burger
	 */
	public void addIngredient(String theType) {
		MyStack<String> ingredHolder = new MyStack<>();
		if (theType == Ingredients.CHEDDAR || theType == Ingredients.MOZZARELLA 
				|| theType == Ingredients.PEPPERJACK) {
			addCheese(ingredHolder, theType, "", "");
			
		} else if (theType == Ingredients.MAYONNAISE || theType == Ingredients.BARON_SAUCE) {			
			addSauce(myBurgerTop, ingredHolder, theType, "");
		
		} else if (theType == Ingredients.KETCHUP || theType == Ingredients.MUSTARD) {
			addSauce(myBurgerBottom, ingredHolder, theType, "");
			
		} else if (theType == Ingredients.LETTUCE || theType == Ingredients.TOMATO
				|| theType == Ingredients.ONIONS) {
			addVeggies(myBurgerTop, ingredHolder, Ingredients.MAYONNAISE, Ingredients.BARON_SAUCE, 
					theType, "", "");
			
		} else if (theType == Ingredients.MUSHROOMS) {
			addVeggies(myBurgerTop, ingredHolder, Ingredients.KETCHUP, Ingredients.MUSTARD, 
					theType, "", "");
		}
	}
	
	/**
	 * Removes the ingredient type from the Burger.
	 * 
	 * @param theType ingredient that will be remove to the Burger
	 */
	public void removeIngredient(String theType) {
		
	}
	
	/**
	 * Helper method that puts cheese on bottom bun. 
	 * 
	 * @param holder holds ingredients
	 * @param cheeseBot cheese on bottom
	 * @param cheeseMid cheese on middle
	 * @param cheeseTop cheese on top
	 */
	private void addCheese(MyStack<String> holder, String cheeseBot, String cheeseMid, 
			String cheeseTop) {	
		
		// Only enter if there are more than 1 patty
		while (myPattyCount > 1) {
			holder.push(myBurgerBottom.pop());
			myPattyCount--;
		}
		myBurgerBottom.push(cheeseBot);
		myBurgerBottom.push(cheeseMid);
		myBurgerBottom.push(cheeseTop);
		while (!holder.isEmpty()) {
			myBurgerBottom.push(holder.pop());
			myPattyCount++;
		}		
	}
	
	/**
	 * Helper method that puts veggies on current bun. 
	 * 
	 * @param currentBun either the top or bottom bun
	 * @param holder holds ingredients
	 * @param sauce1 first sauce on the current bun
	 * @param sauce2 second sauce on the current bun
	 * @param veggieBott veggie on bottom
	 * @param veggieMid veggie in middle
	 * @param veggieTop veggie on top
	 */
	private void addVeggies(MyStack<String> currentBun, MyStack<String> holder, 
			String sauce1, String sauce2, String veggieBott, String veggieMid, String veggieTop) {

		while (currentBun.peek() != sauce1 || currentBun.peek() != sauce2 || 
						currentBun.peek() != Ingredients.BUN) {
			holder.push(myBurgerTop.pop());
		}			
		currentBun.push(veggieBott);
		currentBun.push(veggieMid);	
		currentBun.push(veggieTop);
		while (!holder.isEmpty()) {
			currentBun.push(holder.pop());
		}
	}
	
	/**
	 * Helper method that puts sauce on the current bun. 
	 * 
	 * @param currentBun either top or bottom bun
	 * @param holder hold ingredients
	 * @param bottomSauce sauce on bottom
	 * @param topSauce sauce on top
	 */
	private void addSauce(MyStack<String> currentBun, MyStack<String> holder, 
			String bottomSauce, String topSauce) {
		
		while (currentBun.peek() != Ingredients.BUN) {
			holder.push(currentBun.pop());
		}			
		currentBun.push(bottomSauce);
		currentBun.push(topSauce);			
		while (!holder.isEmpty()) {
			currentBun.push(holder.pop());
		}
	}
		
	/**
	 * Builds a standard Baron Burger.
	 * Constructed in two halves to make adding and changing patties easier.
	 */
	private void makeBaron() {
		myBurgerBottom.push(Ingredients.BUN);
		myBurgerBottom.push(Ingredients.KETCHUP);
		myBurgerBottom.push(Ingredients.MUSTARD);
		myBurgerBottom.push(Ingredients.MUSHROOMS);
		myBurgerBottom.push(Ingredients.BEEF_PATTY);
		myBurgerBottom.push(Ingredients.CHEDDAR);
		myBurgerBottom.push(Ingredients.MOZZARELLA);
		myBurgerBottom.push(Ingredients.PEPPERJACK);
		
		myBurgerTop.push(Ingredients.PICKLE);
		myBurgerTop.push(Ingredients.BUN);
		myBurgerTop.push(Ingredients.MAYONNAISE);
		myBurgerTop.push(Ingredients.BARON_SAUCE);
		myBurgerTop.push(Ingredients.LETTUCE);
		myBurgerTop.push(Ingredients.TOMATO);
		myBurgerTop.push(Ingredients.ONIONS);
	}
	
	/**
	 * Builds a basic burger (buns and patty).
	 * Constructed in two halves to make adding and changing patties easier.
	 */
	private void makeBasic() {
		myBurgerBottom.push(Ingredients.BUN);
		myBurgerBottom.push(Ingredients.BEEF_PATTY);
		
		myBurgerTop.push(Ingredients.BUN);
	}
	
	/**
	 * Converts the Burger to a String for display.
	 * Empties both burger stack fields in process.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		//Builds full burger in correct order (top - bottom) in myBurgerBottom stack.
		while (!myBurgerTop.isEmpty()) {
			myBurgerBottom.push(myBurgerTop.pop());
		}
		//Appends ingredients in myBottomBurger (all ingredients for burger) to string.
		while (!myBurgerBottom.isEmpty()) {
			sb.append(myBurgerBottom.pop());
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append("]");
		return sb.toString();		
	}	
}