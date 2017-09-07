package poopheadgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * First attempt at making a GUI for the poophead card game using the java swing
 * library.
 *
 * @author Calum
 *
 */
public class PoopHeadGUI {

    private static void createCardButton(JPanel playerCards, String cardName) {

        ImageIcon icon;

        //get corresponding card image from deck png images.
        icon = new ImageIcon("resources/deck/" + cardName + ".png");
        //check it exists so it doesn't crash........ 

        //scale image to a specific size
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(66, 110, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        //create card button and add the scaled icon to it so that the icon is above the text
        JButton card = new JButton(cardName);
        card.setIcon(icon);
        card.setVerticalTextPosition(SwingConstants.BOTTOM);
        card.setHorizontalTextPosition(SwingConstants.CENTER);
        card.setPreferredSize(new Dimension(60, 100));

        //remove border and background from card button
        card.setOpaque(false);
        card.setContentAreaFilled(false);
        //card.setBorderPainted(false);
        card.setFocusPainted(false);

        //keep track of player card presses.
        card.addActionListener(cardPlayed);

        playerCards.add(card);

    }

    //Display selected card value in textfield
    private static class PlayerCardSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            Object cardPressed = e.getSource();
            Card handCard = new Card(0);
            int cardPosition = 0;

            //identify hand Card via brute force method
            for (int i = 0; i < hand.getSize(); i++) {
                if (input.equals(hand.getCardString(i))) {
                    //card has been found so move it to pile
                    handCard.setCardNumber(hand.getCard(i).getCardNumber());
                    cardPosition = i;
                    break;
                }
            }

            if (Rules.playable(handCard, pile) == true) {

                if (cardPressed instanceof JButton) {
                    JButton button = (JButton) cardPressed;
                    playerCards.remove(button);

                }

                String text = textFeedback.getText();
                //System.out.printf("%s \n", input);

                textFeedback.setText(text + ", " + input);

                //update pile image
                ImageIcon pileIcon = new ImageIcon("resources/deck/" + input + ".png");
                Image pileImage = pileIcon.getImage();
                Image scaledPileImage = pileImage.getScaledInstance(120, 200, java.awt.Image.SCALE_SMOOTH);
                pileIcon = new ImageIcon(scaledPileImage);
                pileCards.setIcon(pileIcon);
                pileCards.setText(input);
                frame.revalidate();
                frame.repaint();

                //have already identified hand Card so move it to pile CardGroup
                pile.addCard(handCard);
                hand.removeCard(cardPosition);

                
                
                if(Rules.burnable(pile)){
                    //remove cards from pile and reset its icon
                    for(int i=pile.getSize()-1; i >= 0; i--){
                        pile.removeCard(i);
                    }
                    
                ImageIcon resetPileIcon = new ImageIcon("resources/pile.png");
                Image resetPileImage = resetPileIcon.getImage();
                Image scaledResetPileImage = resetPileImage.getScaledInstance(120, 200, java.awt.Image.SCALE_SMOOTH);
                resetPileIcon = new ImageIcon(scaledResetPileImage);
                pileCards.setIcon(resetPileIcon);
                pileCards.setText("pile");
                frame.revalidate();
                frame.repaint();
                }
                
                
                //check to see current state of pile, deck, and hand
                System.out.printf("deck size = %d, hand size = %d, pile size = %d\n", deck.getSize(), hand.getSize(), pile.getSize());
            } else {
                System.out.printf("Card not playable\n");
            }
        }
    }

    //add a deck card to hand
    private static class PickUpDeckCard implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //if deck still has cards in it then give one to player when it is selected
            //and add new card button to player hand for this card
            if (deck.getSize() >= 1) {
                int currentCard = deck.getSize() - 1;
                String card = deck.getCardString(currentCard);
                //System.out.printf(card);

                //move card to hand from deck, and add visualisation in hand panel
                hand.addCard(deck.getCard(currentCard));
                deck.removeCard(currentCard);
                createCardButton(playerCards, card);

            } else {
                //deck has no more cards in it so remove the deck button
                Object deckPressed = e.getSource();
                if (deckPressed instanceof JButton) {
                    JButton button = (JButton) deckPressed;
                    deckCards.remove(button);

                }
                //remove deck button
                deckCards.setVisible(false);
            }
            // playerCards.setLayout(new GridLayout(2, 10, 25, 25));
            frame.revalidate();
            frame.repaint();

            //check to see current state of pile, deck, and hand
            System.out.printf("deck size = %d, hand size = %d, pile size = %d\n", deck.getSize(), hand.getSize(), pile.getSize());
        }
    }

    //create and shuffle intial empty deck of cards at start of game
    private static void intialiseDeck(CardGroup deck) {
        for (int i = 0; i < 52; i++) {
            deck.addCard(new Card(i + 1));
        }

       deck.shuffleCards();
    }

    //************ Game declarations ************\\
    public static CardGroup deck = new CardGroup();
    public static CardGroup hand = new CardGroup();
    public static CardGroup pile = new CardGroup();

    //************ GUI declarations *************\\
    // Define different components, need to be defined here so that values are accessible to other functions?
    //needed / essential for desired functionality
    private static JFrame frame;  //window to hold everything
    private static JPanel communityCards; // panel to hold shared cards
    private static JLabel pileCards;      //pile representation added to communityCards
    private static JButton deckCards;     //deck representation added to communityCards
    private static JPanel playerCards;    // panel to hold player card buttons

    //temporary text field to give feedback on buttons pressed.
    private static JTextField textFeedback; //textfeedback area

    //not needed / unknown purpose for desired funtionality
    private static boolean start = true; //no idea yet

    // Define the action listeners
    private static ActionListener cardPlayed = new PlayerCardSelected();
    private static ActionListener getDeckCard = new PickUpDeckCard();

    //************************************************************************\\
    //----------------------       MAIN METHOD       -------------------------\\
    //************************************************************************\\
    public static void main(String[] args) {

        intialiseDeck(deck);

        //********** GUI setup ***************\\
        // Add the frame, panel and text field
        frame = new JFrame("Poophead");
        frame.setPreferredSize(new Dimension(1900, 800));

        //top section of JFrame window
        textFeedback = new JTextField();
        // Set the result field
        textFeedback.setText("Card history: ");
        textFeedback.setHorizontalAlignment(SwingConstants.RIGHT);
        textFeedback.setEditable(false);
        textFeedback.setColumns(13);

        //second section of JFrame window for deck and pile cards
        communityCards = new JPanel();
        pileCards = new JLabel("pile");
        pileCards.setVerticalTextPosition(SwingConstants.BOTTOM);
        pileCards.setHorizontalTextPosition(SwingConstants.CENTER);

        //set image to represent the pile.
        ImageIcon pileIcon = new ImageIcon("resources/pile.png");
        Image pileImage = pileIcon.getImage();
        Image scaledPileImage = pileImage.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
        pileIcon = new ImageIcon(scaledPileImage);
        pileCards.setIcon(pileIcon);

        //set image to represent the pile
        deckCards = new JButton("deck");
        deckCards.setVerticalTextPosition(SwingConstants.BOTTOM);
        deckCards.setHorizontalTextPosition(SwingConstants.CENTER);

        ImageIcon deckIcon = new ImageIcon("resources/cardBack.png");
        Image deckImage = deckIcon.getImage();
        Image scaledDeckImage = deckImage.getScaledInstance(80, 100, java.awt.Image.SCALE_SMOOTH);
        deckIcon = new ImageIcon(scaledDeckImage);
        deckCards.setIcon(deckIcon);

        //remove border and background from deck button
        deckCards.setOpaque(false);
        deckCards.setContentAreaFilled(false);
        //deckCards.setBorderPainted(false);
        deckCards.setFocusPainted(false);
        deckCards.addActionListener(getDeckCard);

        // Set the content panel
        //top, left, bottom, right border widths (applies to all attached panels)
        communityCards.setBorder(new EmptyBorder(10, 10, 10, 10));
        communityCards.setLayout(new BorderLayout(1, 2));
        communityCards.add(pileCards, BorderLayout.LINE_START);
        communityCards.add(deckCards, BorderLayout.LINE_END);

        //third and final section of JFrame window for player's hand cards.
        playerCards = new JPanel();
        // Set the content panel
        //top, left, bottom, right border widths (applies to all attached panels)
        playerCards.setBorder(new EmptyBorder(5, 5, 5, 5));
        playerCards.setLayout(new GridLayout(1, 26, 1, 1));

//        createCardButton(playerCards, "Ah");
//        createCardButton(playerCards, "2d");
//        createCardButton(playerCards, "3c");
//        createCardButton(playerCards, "4s");
        // Settings for the frame
        frame.setLayout(new GridLayout(3, 1)); //frame has 3 vertical sections
        frame.add(textFeedback);    //1st section
        frame.add(communityCards);       //2nd section
        frame.add(playerCards);     //3rd section
        frame.pack();               //sizes components to at least default sizes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
