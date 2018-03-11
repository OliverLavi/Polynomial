package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node poly1t = poly1;
		Node poly2t = poly2;
		Node poly3 = null;
		Node poly3t = poly3;
		
		//Loop runs while there are still terms to add
		while(poly1t != null || poly2t != null) {
			
			//If both polynomials are empty
			if(poly1t == null && poly2t == null) {
				return null;
			}
			
			//If one polynomial is empty or already completely added
			if(poly1t == null) {
				poly3 = new Node(poly2t.term.coeff, poly2t.term.degree, poly3);
				poly2t = poly2t.next;
				continue;
			}else if(poly2t == null){
				poly3 = new Node(poly1t.term.coeff, poly1t.term.degree, poly3);
				poly1t = poly1t.next;
				continue;
			}

			//Evaluate degrees in both polynomials and add them accordingly
			if(poly1t.term.degree == poly2t.term.degree) {
				poly3 = new Node(poly1t.term.coeff + poly2t.term.coeff, poly1t.term.degree, poly3);
				poly1t = poly1t.next;
				poly2t = poly2t.next;
			}else if(poly1t.term.degree > poly2t.term.degree) {
//				if(poly3 == null) {
//					poly3 = new Node(poly2t.term.coeff, poly2t.term.degree, poly3);
//					poly2t = poly2t.next;
//				}else {
					poly3 = new Node(poly2t.term.coeff, poly2t.term.degree, poly3);
					poly2t = poly2t.next;
				//}
			}else if(poly1t.term.degree < poly2t.term.degree){
				poly3 = new Node(poly1t.term.coeff, poly1t.term.degree, poly3);
				poly1t = poly1t.next;
			}
		}
		
		//Reverses polynomial
		Node currentPtr = poly3;
		Node prevPtr = null;
		Node nextPtr = null;
		while(currentPtr!= null) {
			nextPtr = currentPtr.next;
			currentPtr.next = prevPtr;
			prevPtr = currentPtr;
			currentPtr = nextPtr;
		}
		poly3 = prevPtr;

		//Removes zero coefficient terms from polynomial
		Node ptr = poly3;
		Node ptr2 = poly3.next;
		if(ptr.term.coeff==0) {
			poly3 = poly3.next;
		}
		while(ptr2 != null) {
			if (ptr2.term.coeff == 0) {
				ptr.next = ptr2.next;
				ptr2 = ptr2.next;
				continue;
			}
			ptr2 = ptr2.next;
			ptr = ptr.next;
			
		}
		
		
		return poly3;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node poly1t = poly1;
		Node poly2t = poly2;
		Node poly3 = null;

		poly2t = poly2;
		while(poly1t != null) {
			Node poly4 = null;
			poly2t = poly2;
			while(poly2t != null) {
				poly4 = null;
				poly4 = new Node(poly1t.term.coeff * poly2t.term.coeff, 
						poly1t.term.degree + poly2t.term.degree, 
						poly4);
				poly3 = add(poly3, poly4);
				poly2t = poly2t.next;
			}
			poly1t = poly1t.next;
		}
		
		return poly3;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		float sum = 0;
		Node ptr = poly;
		while(ptr != null) {
			sum += (ptr.term.coeff * Math.pow(x, ptr.term.degree));
			ptr = ptr.next;
		}
		
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
