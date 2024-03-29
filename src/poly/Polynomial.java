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
	
	private static Node addLast(Node head, float coeff, int degree) {
       Node newLL = head;

       if (head == null) {
           return new Node(coeff,degree,null);
       }

       while ((head.next != null)) {
           head = head.next;
       }
       
       head.next = new Node(coeff,degree,null);
       return newLL;
   }	
	
	private static Node removeDuplicates(Node head) {
		
		Node ptr1, ptr2;
	    ptr1 = head;  
	  
	    while (ptr1 != null && ptr1.next != null) {  
	        ptr2 = ptr1;  
	  

	        while (ptr2.next != null) {  
	  

	            if (ptr1.term.degree == ptr2.term.degree) {  
	                ptr1.term.coeff = ptr1.term.coeff + ptr2.term.coeff;  
	                ptr2.term.coeff = 0;
	  
	            }  
	            else {
	                ptr2 = ptr2.next; 
	            }
	        }  
	        ptr1 = ptr1.next;  
	    }  
		return head;
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

		if(poly1 == null) {
			return poly2;
		}
		if(poly2 == null) {
			return poly1;
		}
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		
		Node sum = null;

		while(ptr1 != null && ptr2 != null) {
			
			
			if(ptr1.term.degree > ptr2.term.degree) {
				sum = addLast(sum,ptr2.term.coeff,ptr2.term.degree);
				ptr2=ptr2.next;
			}
			else if(ptr1.term.degree < ptr2.term.degree) {
				sum = addLast(sum,ptr1.term.coeff,ptr1.term.degree);
				ptr1=ptr1.next;
			}
			else {
				sum = addLast(sum,ptr1.term.coeff+ptr2.term.coeff,ptr1.term.degree);
				
				
				ptr1=ptr1.next;
				ptr2=ptr2.next;
			}
			
			
		}
		
		if(ptr1 == null) {
			while(ptr2 != null) {
				sum = addLast(sum,ptr2.term.coeff,ptr2.term.degree);
				ptr2 = ptr2.next;
			}
		}
		if(ptr2 == null) {
			while(ptr1 != null) {
				sum = addLast(sum,ptr1.term.coeff,ptr1.term.degree);
				ptr1 = ptr1.next;
			}
		}
		
		//REMOVE ZERO TERMS 
		// 
		//
		
		Node ptr = sum;
		Node prev = null;

		while(ptr != null) {
			
			if(ptr.term.coeff == 0 && prev == null) {
				sum = sum.next;
				ptr = sum;
				continue;
			}
			if(ptr.term.coeff == 0 && prev != null) {
				prev.next = ptr.next;
				
			}
			prev = ptr;
			ptr = ptr.next;
		}
		
		
		return sum;
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
		
		if(poly1 == null || poly2 == null) {
			return null;
		}
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		
		Node product = null;
		
		while(ptr1 != null ) {
			
			Node newList = null;
			
			while(ptr2 != null) {
				//product = addLast(product,ptr1.term.coeff*ptr2.term.coeff,ptr1.term.degree+ptr2.term.degree);
				
				newList = addLast(newList,ptr1.term.coeff*ptr2.term.coeff,ptr1.term.degree+ptr2.term.degree);
				ptr2 = ptr2.next;
				
				
				
				
			}
			
			product = Polynomial.add(product,newList);
			
			ptr2 = poly2;
			ptr1 = ptr1.next;
		}
		//product = removeDuplicates(product);
		
		/*
	    ptr1 = product;  
	  
	    while (ptr1 != null) {  
	        ptr2 = ptr1;  
	  

	        while (ptr2.next != null) {  
	  

	            if (ptr1.term.degree == ptr2.term.degree) {  
	                ptr1.term.coeff = ptr1.term.coeff + ptr2.term.coeff;  
	                ptr2.term.coeff = 0;
	  
	            }  
	            else {
	                ptr2 = ptr2.next; 
	            }
	        }  
	        ptr1 = ptr1.next;  
	    }  
	    */
		
		Node ptr = product;
		Node prev = null;
		
		

		while(ptr != null) {
			
			if(ptr.term.coeff == 0 && prev == null) {
				product = product.next;
				ptr = product;
				continue;
			}
			if(ptr.term.coeff == 0 && prev != null) {
				prev.next = ptr.next;
				
			}
			prev = ptr;
			ptr = ptr.next;
		}
		
		return product;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {

		if(poly == null) {
			return 0;
		}
		float result = 0;
		
		Node ptr = poly;
		while( ptr != null ) {
			result += ptr.term.coeff*(float)(Math.pow(x, ptr.term.degree));
			
			ptr = ptr.next;
		}
		//printDick();
		
		
		return result;
	}
	
	private static void printDick() {
		System.out.println("8====D");
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
