/**
 * professor: Patti ordonez
 * Section: 0U1
 * Name: MyStack.java
 * Purpose: creates a stack to store cards from the Card class.
 */

import java.util.*;


public class MyStack {
    LinkedList<Card> stack;

    /**
     * Constructor for MyStack
     */
    public MyStack() {
        stack = new LinkedList<Card>();
    }

    /**
     * checks top card of stack
     */
    public Card topCard()
    {
        if(stack.size() == 0)
            return null;
        else
            return stack.getLast();
    }

    /**
     * adds card to top of stack
     */
    public void addCard(Card card) {
        stack.add(card);
    }
    
    /**
     * removes top card from stack
     */
    public Card removeCard() {
        if (stack.size() == 0)
            return null;
        else
            return stack.removeLast();
    }

    /**
     * checks if stack is empty
     */
    public boolean isEmpty() {
        return stack.size() == 0;
    }

    /**
     * returns stack as a string
    */
    public String toString() {
        String temp = new String();
        for (int i = 0; i < stack.size();i++){
            temp = temp + " " + stack.get(i).toString();
        }
        return temp;
    }


    public static void main(String args[]){
        MyStack stack = new MyStack();
        Card card00 = new Card(Card.suit[0], Card.rank[0]);
        Card card14 = new Card(Card.suit[1], Card.rank[4]);

        System.out.println(stack.isEmpty());

        stack.addCard(card00);
        stack.addCard(card14);

        System.out.println(stack.toString());

        stack.removeCard();

        System.out.println(stack.toString());

    }

}
