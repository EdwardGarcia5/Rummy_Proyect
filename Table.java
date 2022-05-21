import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
*    This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*    The GUI is simulating a playing table
    @author Patti Ordonez
*/
public class Table extends JFrame implements ActionListener
{
    final static int numDealtCards = 9;
    JPanel player1;
    JPanel player2;
    JPanel deckPiles;
    JLabel deck;
    JLabel stack;
    JList p1HandPile;
    JList p2HandPile;
    Deck cardDeck;
    MyStack stackDeck;
    
    SetPanel [] setPanels = new SetPanel[13];
    JLabel topOfStack;
    JLabel deckPile;
    JLabel pt;
    JButton p1Stack;
    JButton p2Stack;
    
    JButton p1Deck;
    JButton p2Deck;
    
    JButton p1Lay;
    JButton p2Lay;
    
    JButton p1LayOnStack;
    JButton p2LayOnStack;
    
    JPanel pturn;
    
    DefaultListModel p1Hand;
    DefaultListModel p2Hand;
    
    Hand cardsPlayer1;
    Hand cardsPlayer2;


    
    boolean turn = true;
    boolean draw = false;
    

    private void deal(Hand cards)
    {
        for(int i = 0; i < numDealtCards; i ++)
            cards.addCard((Card)cardDeck.dealCard());
        cards.sort();
    }

    public Table()
    {
        super("The Card Game of the Century");
        setLayout(new BorderLayout());
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardDeck = new Deck();

        for(int i = 0; i < Card.suit.length; i++){
            for(int j = 0; j < Card.rank.length; j++){
                Card card = new Card(Card.suit[i],Card.rank[j]);
                cardDeck.addCard(card);
            }
        }
        cardDeck.shuffle();
        stackDeck = new MyStack();

        JPanel top = new JPanel();

        for (int i = 0; i < Card.rank.length;i++)
            setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


        top.add(setPanels[0]);
        top.add(setPanels[1]);
        top.add(setPanels[2]);
        top.add(setPanels[3]);
        
        pt = new JLabel("player one's turn");
        pturn = new JPanel();
        
        pturn.add(pt);
        
        
        player1 = new JPanel(new GridLayout(2,1));
        player1.add(pturn);
        player1.add(top);


        add(player1, BorderLayout.NORTH);
        JPanel bottom = new JPanel();


        bottom.add(setPanels[4]);
        bottom.add(setPanels[5]);
        bottom.add(setPanels[6]);
        bottom.add(setPanels[7]);
        bottom.add(setPanels[8]);

        
        player2 = new JPanel();
        player2.add(bottom);
        
        
        add(player2, BorderLayout.SOUTH);
        
        
        JPanel middle = new JPanel(new GridLayout(1,3));

        
        p1Stack = new JButton("Stack");
        p1Stack.addActionListener(this);
        p1Deck = new JButton("Deck ");
        p1Deck.addActionListener(this);
        p1Lay = new JButton("Lay  ");
        p1Lay.addActionListener(this);
        p1LayOnStack = new JButton("LayOnStack");
        p1LayOnStack.addActionListener(this);


        cardsPlayer1 = new Hand();
        deal(cardsPlayer1);
        p1Hand = new DefaultListModel();
        for(int i = 0; i < cardsPlayer1.getNumberOfCards(); i++)
            p1Hand.addElement(cardsPlayer1.getCard(i));
        p1HandPile = new JList(p1Hand);


        middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));

        
        deckPiles = new JPanel();
        deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
        deckPiles.add(Box.createGlue());
        JPanel left = new JPanel();
        left.setAlignmentY(Component.CENTER_ALIGNMENT);


        stack = new JLabel("Stack");
        stack.setAlignmentY(Component.CENTER_ALIGNMENT);

        left.add(stack);
        topOfStack = new JLabel();
        topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
        topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
        left.add(topOfStack);
        deckPiles.add(left);
        deckPiles.add(Box.createGlue());

        JPanel right = new JPanel();
        right.setAlignmentY(Component.CENTER_ALIGNMENT);

        deck = new JLabel("Deck");

        deck.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deck);
        deckPile = new JLabel();
        deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
        deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deckPile);
        deckPiles.add(right);
        deckPiles.add(Box.createGlue());
        middle.add(deckPiles);


        
        
        try {
            File fi = new File("p2-output.txt"); 
            fi.delete();
            
            String pih1 = cardsPlayer1.toString();
            pih1 = pih1.substring(1, pih1.length() - 1);
            System.out.println("Initial Player 1: " + pih1);

            BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
            writer.write("Initial Player 1: " + pih1);

            p2Stack = new JButton("Stack");
            p2Stack.addActionListener(this);
            p2Deck = new JButton("Deck ");
            p2Deck.addActionListener(this);
            p2Lay = new JButton("Lay  ");
            p2Lay.addActionListener(this);
            p2LayOnStack = new JButton("LayOnStack");
            p2LayOnStack.addActionListener(this);

            cardsPlayer2 = new Hand();
            deal(cardsPlayer2);
            p2Hand = new DefaultListModel();

            for(int i = 0; i < cardsPlayer2.getNumberOfCards(); i++)
                p2Hand.addElement(cardsPlayer2.getCard(i));

            p2HandPile = new JList(p2Hand);

            String pih2 = cardsPlayer2.toString();
            pih2 = pih2.substring(1, pih2.length() - 1);
            System.out.println("Initial Player 2: " + pih2);

            writer.append("\nInitial Player 2: " + pih2);

            System.out.println("Player 1");
            
            writer.append("\nPlayer 1");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));

        add(middle, BorderLayout.CENTER);

        JPanel leftBorder = new JPanel(new GridLayout(2,1));


        setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
        setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
        leftBorder.add(setPanels[9]);
        leftBorder.add(setPanels[10]);
        add(leftBorder, BorderLayout.WEST);

        JPanel rightBorder = new JPanel(new GridLayout(2,1));

        setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
        setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
        rightBorder.add(setPanels[11]);
        rightBorder.add(setPanels[12]);
        add(rightBorder, BorderLayout.EAST);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if(p1Deck == src|| p2Deck == src){

            Card card = cardDeck.dealCard();

            if (card != null && draw == false){
                if(src == p1Deck){
                    if(turn == true){
                        p1Hand.clear();
                        cardsPlayer1.addCard(card);
                        cardsPlayer1.sort();
                        for(int i = 0; i < cardsPlayer1.getNumberOfCards(); i++)
                            p1Hand.addElement(cardsPlayer1.getCard(i));
                        draw = true;
                    }
                }
                else{
                    if(turn == false){
                        p2Hand.clear();
                        cardsPlayer2.addCard(card);
                        cardsPlayer2.sort();
                        for(int i = 0; i < cardsPlayer2.getNumberOfCards(); i++)
                            p2Hand.addElement(cardsPlayer2.getCard(i));
                        draw = true;
                    }
                }
                
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    writer.append("\n\tAdded: " + card.toString());
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("\tAdded: " + card.toString());
            }

            if(cardDeck.getSizeOfDeck() == 0){
                deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
                winner(cardsPlayer1,cardsPlayer2);
            }
        }
        
        if(p1Stack == src || p2Stack == src){

            Card card = stackDeck.removeCard();

            if(card != null && draw == false  ){
                Card topCard = stackDeck.topCard();
                
                if (topCard != null) {
                    topOfStack.setIcon(topCard.getCardImage());
                }
                else{
                    topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
                }
                    
                if(p1Stack == src){
                    p1Hand.clear();
                    cardsPlayer1.addCard(card);
                    cardsPlayer1.sort();
                    for(int i = 0; i < cardsPlayer1.getNumberOfCards(); i++)
                        p1Hand.addElement(cardsPlayer1.getCard(i));
                    draw = true;
                }
                
                else{
                    p2Hand.clear();
                    cardsPlayer2.addCard(card);
                    cardsPlayer2.sort();
                    for(int i = 0; i < cardsPlayer2.getNumberOfCards(); i++)
                        p2Hand.addElement(cardsPlayer2.getCard(i));
                    draw = true;
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    writer.append("\n\tAdded: " + card.toString());
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("\tAdded: " + card.toString());
            }
        }

        if(p1Lay == src && turn == true){
            Object [] cards = p1HandPile.getSelectedValues();
            if (cards != null && cards.length > 0){
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    System.out.print("\tDiscarded: ");
                    writer.append("\n\tDiscarded: ");

                    for(int i = 0; i < cards.length; i++)
                    {
                        Card card = (Card)cards[i];
                        
                        if(i != 0){
                            System.out.print(", " + card.toString());
                            writer.append(", " + card.toString());
                        }else{
                            System.out.print(card.toString());
                            writer.append(card.toString());
                        }
                        
                        layCard(card);
                        p1Hand.removeElement(card);
                    }


                    cardsPlayer1.discardHand();
                    for(int i = 0; i < p1Hand.size(); i++){
                        cardsPlayer1.addCard((Card)p1Hand.get(i));
                    }

                    String psh = cardsPlayer1.toString();
                    psh = psh.substring(1, psh.length() - 1);
                    System.out.println("\n\tHand now: " + psh);
                    writer.append("\n\tHand now: " + psh);

                    
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                

                if(cardsPlayer1.isEmpty()){
                    winner(cardsPlayer1,cardsPlayer2);
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    System.out.println("Player 2");
                    writer.append("\nPlayer 2");
                    pt.setText("player two's turn");
                    turn = false;
                    draw = false;
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                
            }
        }

        if(p2Lay == src && turn == false){
            Object [] cards = p2HandPile.getSelectedValues();
            if (cards != null && cards.length > 0){
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    System.out.print("\tDiscarded: ");
                    writer.append("\n\tDiscarded: ");
                    for(int i = 0; i < cards.length; i++)
                    {
                        Card card = (Card)cards[i];
                        if(i != 0){
                            System.out.print(", " + card.toString());
                            writer.append(", " + card.toString());
                        }else{
                            System.out.print(card.toString());
                            writer.append(card.toString());
                        }
                        
                        layCard(card);
                        p2Hand.removeElement(card);
                    }
                    System.out.println("");
                    
                    
                    cardsPlayer2.discardHand();
                    for(int i = 0; i < p2Hand.size(); i++){
                        cardsPlayer2.addCard((Card)p2Hand.get(i));
                    }
                    
                    String psh = cardsPlayer2.toString();
                    psh = psh.substring(1, psh.length() - 1);
                    System.out.println("\n\tHand now: " + psh);
                    writer.append("\n\tHand now: " + psh);

                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                

                if(cardsPlayer2.isEmpty()){
                    winner(cardsPlayer1,cardsPlayer2);
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                    System.out.println("Player 1");
                    writer.append("\nPlayer 1");
                    pt.setText("player one's turn");
                    turn = true;
                    draw = false;

                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

        if(p1LayOnStack == src && turn == true){
            int [] num  = p1HandPile.getSelectedIndices();
            if (num.length == 1)
            {
                Object obj = p1HandPile.getSelectedValue();
                if (obj != null)
                {
                    p1Hand.removeElement(obj);
                    Card card = (Card)obj;
                    stackDeck.addCard(card);
                    topOfStack.setIcon(card.getCardImage());
                    
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                        System.out.println("\tDiscarded: " + card.toString());
                        writer.append("\n\tDiscarded: " + card.toString());

                        String psh = cardsPlayer1.toString();
                        psh = psh.substring(1, psh.length() - 1);
                        System.out.println("\tHand now: " + psh);                        
                        writer.append("\n\tHand now: " + psh);

                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    if(cardsPlayer1.isEmpty()){
                        winner(cardsPlayer1,cardsPlayer2);
                    }
                    
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                        writer.append("\nPlayer 2");
                        System.out.println("Player 2");
                        pt.setText("player two's turn");
                        turn = false;
                        draw = false;

                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    
                    
                }
            }
        }

        if(p2LayOnStack == src && turn == false){
            int [] num  = p2HandPile.getSelectedIndices();
            if (num.length == 1)
            {
                Object obj = p2HandPile.getSelectedValue();
                if (obj != null)
                {
                    p2Hand.removeElement(obj);
                    Card card = (Card)obj;
                    stackDeck.addCard(card);
                    topOfStack.setIcon(card.getCardImage());  

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                        System.out.println("\tDiscarded: " + card.toString());
                        writer.append("\n\tDiscarded: " + card.toString());

                        String psh = cardsPlayer2.toString();
                        psh = psh.substring(1, psh.length() - 1);
                        System.out.println("\tHand now: " + psh);
                        writer.append("\n\tHand now: " + psh);

                        writer.append("\nPlayer 1");
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    if(cardsPlayer2.isEmpty()){
                        winner(cardsPlayer1,cardsPlayer2);
                    }

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
                        System.out.println("Player 1");
                        writer.append("\nPlayer 1");

                        pt.setText("player one's turn");
                        turn = true;
                        draw = false;
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }


    void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}
    
    
    void winner(Hand p1, Hand p2) {   
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("p2-output.txt", true));
            System.out.println("points: " + p1.evaluateHand() + " to " + p2.evaluateHand());
            if (p1.evaluateHand() < p2.evaluateHand()){
                writer.append("\nPlayer 1 Wins!!!!");
                System.out.println("Player 1 Wins!!!!");
            }else if(p1.evaluateHand() == p2.evaluateHand()){
                writer.append("\nits a tie!!!!");
                System.out.println("its a tie!!!!");
            }else{
                writer.append("\nPlayer 2 Wins!!!!");
                System.out.println("Player 2 Wins!!!!");
            }
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }

}

class HandPanel extends JPanel
{

    public HandPanel(String name, JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack)
    {
        //model = hand.createSelectionModel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //        add(Box.createGlue());
        JLabel label = new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        stack.setAlignmentX(Component.CENTER_ALIGNMENT);
        //        add(Box.createGlue());
        add(stack);
        deck.setAlignmentX(Component.CENTER_ALIGNMENT);
        //        add(Box.createGlue());
        add(deck);
        lay.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lay);
        layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(layOnStack);
        add(Box.createGlue());
        add(hand);
        add(Box.createGlue());
    }

}

class SetPanel extends JPanel
{
    private Set data;
    JButton [] array = new JButton[4];
    public SetPanel(int index)
    {
        super();
        data = new Set(Card.rank[index]);

        for(int i = 0; i < array.length; i++){
            array[i] = new JButton("   ");
            add(array[i]);
        }
    }
}
