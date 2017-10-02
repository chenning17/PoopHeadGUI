package poopheadgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //************************************************************************\\
    //                       COMPONENT SET UP FUNCTIONS                       \\
    //************************************************************************\\
    static void setAppearance(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        //card.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(60, 100));
    }

    //create and shuffle intial empty deck of cards at start of game
    private static void intialiseDeck(CardGroup deck) {
        for (int i = 0; i < 52; i++) {
            deck.addCard(new Card(i + 1));
        }

        deck.shuffleCards();
    }

    private static void initialiseText(JTextField textFeedback, String text) {
        // Set the result field
        textFeedback.setText(text);
        textFeedback.setHorizontalAlignment(SwingConstants.RIGHT);
        textFeedback.setEditable(false);
        textFeedback.setColumns(13);
    }

    private static void initialisePile() {
        pileCards = new JLabel("pile");
        pileCards.setVerticalTextPosition(SwingConstants.BOTTOM);
        pileCards.setHorizontalTextPosition(SwingConstants.CENTER);
        setIcon(pileCards, "resources/pile.png");
    }

    private static void initialiseDeck() {
        deckCards = new JButton("deck");
        deckCards.setVerticalTextPosition(SwingConstants.BOTTOM);
        deckCards.setHorizontalTextPosition(SwingConstants.CENTER);
        setIcon(deckCards, "resources/cardBack.png");

        //remove border and background from deck button
        deckCards.setOpaque(false);
        deckCards.setContentAreaFilled(false);
        //deckCards.setBorderPainted(false);
        deckCards.setFocusPainted(false);
        deckCards.addActionListener(getDeckCard);
    }

    private static void initialiseCommunity() {
        // add pile and deck to community and set size/layout
        //top, left, bottom, right border widths (applies to all attached panels)
        communityCards.setBorder(new EmptyBorder(10, 10, 10, 10));
        communityCards.setLayout(new BorderLayout(1, 2));
        communityCards.add(pileCards, BorderLayout.LINE_START);
        communityCards.add(deckCards, BorderLayout.LINE_END);

    }

    private static void initialiseFrame() {
        // set layout and add components to frame
        frame.setLayout(new GridLayout(4, 4)); //frame has 3 vertical sections
        frame.add(opponentPanel);
        frame.add(textFeedback);    //1st section
        frame.add(communityCards);       //2nd section
        frame.add(playerCards);     //3rd section
        frame.pack();               //sizes components to at least default sizes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void dealCards() {
        for (int i = 0; i < 3; i++) {
            receiveDeckCard();
            opponentReceiveDeckCard(0);
            opponentReceiveDeckCard(1);
        }
    }

    //************************************************************************\\
    //                      COMPONENT ALTERING FUNCTIONS                      \\
    //************************************************************************\\
    //
    // VISUAL FUNCTIONS //
    static void burnPile(CardGroup pile, JLabel pileLabel, JFrame frame) {
        //remove cards from pile and reset its icon
        for (int i = pile.getSize() - 1; i >= 0; i--) {
            pile.removeCard(i);
        }

        setIcon(pileLabel, "resources/burn.png");
        pileLabel.setText("pile");
        initialiseText(textFeedback, "Card history: ");
        frame.revalidate();
        frame.repaint();
    }

    static void updateFeedback(JTextField textFeedback, String cardName) {
        String text = textFeedback.getText();
        //System.out.printf("%s \n", input);
        textFeedback.setText(text + ", " + cardName);
                      

        frame.revalidate();
        frame.repaint();

    }

    static void setIcon(JButton button, String pathToIcon) {

        ImageIcon icon = new ImageIcon(pathToIcon);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(80, 120, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        button.setIcon(icon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    static void setIcon(JLabel label, String pathToIcon) {
        //set image as icon for label
        ImageIcon pileIcon = new ImageIcon(pathToIcon);
        Image pileImage = pileIcon.getImage();
        Image scaledPileImage = pileImage.getScaledInstance(80, 120, java.awt.Image.SCALE_SMOOTH);
        pileIcon = new ImageIcon(scaledPileImage);
        label.setIcon(pileIcon);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    // PLAYER CARD FUNCTIONS //
    static void playCard(Object cardPressed, Card handCard, int cardPosition, String cardName) {
        //move card from hand to pile, and update buttons
        if (cardPressed instanceof JButton) {
            removeCardButton(cardPressed, playerCards);
            pile.addCard(handCard);
            hand.removeCard(cardPosition);
            setIcon(pileCards, "resources/deck/" + cardName + ".png");

            if (!(Rules.enoughCards(hand, deck))) {
                //need to give a deck card to player as not enough cards
                receiveDeckCard();
                if (deck.getSize() == 0) {
                    //if after automatically receiving deck card the deck becomes empty, remove it
                    deckCards.setVisible(false);
                }
            }

            frame.revalidate();
            frame.repaint();
        }
    }

    static void receiveDeckCard() {
        int currentCard = deck.getSize() - 1;
        String card = deck.getCardString(currentCard);
        //System.out.printf(card);

        //move card to hand from deck, and add visualisation in hand panel
        hand.addCard(deck.getCard(currentCard));
        deck.removeCard(currentCard);
        createCardButton(playerCards, card);
    }

    static void removeCardButton(Object cardPressed, JPanel cardPanel) {
        if (cardPressed instanceof JButton) {
            JButton button = (JButton) cardPressed;
            cardPanel.remove(button);

        }
    }

    private static void createCardButton(JPanel playerCards, String cardName) {

        JButton card = new JButton(cardName);
        setIcon(card, "resources/deck/" + cardName + ".png");
        setAppearance(card);

        //keep track of player card presses.
        card.addActionListener(cardPlayed);

        playerCards.add(card);

    }

    // OPPONENT CARD FUNCTIONS //
    static void removeCardLabel(JPanel panel) {
        Object temp = panel.getComponent(0);

        if (temp instanceof JLabel) {
            JLabel tempLabel = (JLabel) temp;
            panel.remove(tempLabel);
        }
    }

    private static void opponentPlayCard(JPanel cardPanel, int opponentNumber) {
        //move card from hand to pile, and update buttons

        int cardPosition = 0;

        Card tempPlayed = new Card(0);

        for (int i = 0; i < opponentHand[opponentNumber].getSize(); i++) {
            System.out.printf("%s ", opponentHand[opponentNumber].getCard(i).stringCard());
        }
        System.out.printf("opponent %d\n", opponentNumber);

        while (cardPosition < opponentHand[opponentNumber].getSize()) {
            tempPlayed.setCardNumber(opponentHand[opponentNumber].getCard(cardPosition).getCardNumber());
            if (Rules.playable(tempPlayed, pile)) {
                removeCardLabel(cardPanel); //remove a card representative label from panel

                String cardName = opponentHand[opponentNumber].getCard(cardPosition).stringCard();

                pile.addCard(tempPlayed);
                opponentHand[opponentNumber].removeCard(cardPosition);

                setIcon(pileCards, "resources/deck/" + cardName + ".png");

                updateFeedback(textFeedback, cardName);

                System.out.printf("opponent %d played card %d, card: %s\n", opponentNumber, cardPosition, cardName);

                if (!(Rules.enoughCards(opponentHand[opponentNumber], deck))) {
                    //need to give a deck card to player as not enough cards
                    opponentReceiveDeckCard(opponentNumber);
                    if (deck.getSize() == 0) {
                        //if after automatically receiving deck card the deck becomes empty, remove it
                        deckCards.setVisible(false);
                    }

                    //   System.out.printf("after being dealt new cards\n", cardPosition, cardName);
                    for (int i = 0; i < opponentHand[opponentNumber].getSize(); i++) {
                        //       System.out.printf("%s ", opponentHand[opponentNumber].getCard(i).stringCard());
                    }
                }

                if (Rules.burnable(pile)) {
                    burnPile(pile, pileCards, frame);
                }

                frame.revalidate();
                frame.repaint();
                return;

            } else {
                //  System.out.printf("opponent not able to play card %d\n", cardPosition);
                cardPosition++;
            }

        }

        //pick up pile function
        opponentPickUpPile(opponentNumber);
        System.out.printf("...opponent %d has no playable cards so must pick up pile...\n\n", opponentNumber);
    }

    private static void createVisualOpponentCard(int opponentNumber) {

        JLabel card = new JLabel("opponent " + Integer.toString(opponentNumber));
        setIcon(card, "resources/cardBack.png");
        opponentVisual[opponentNumber].add(card);
        opponentPanel.add(opponentVisual[opponentNumber]);
    }

    static void opponentReceiveDeckCard(int opponentNumber) {
        int currentCard = deck.getSize() - 1;
        String card = deck.getCardString(currentCard);
        // System.out.println(card);

        //move card to hand from deck, and add visualisation in hand panel
        opponentHand[opponentNumber].addCard(deck.getCard(currentCard));
        deck.removeCard(currentCard);

        createVisualOpponentCard(opponentNumber);

    }

    static void opponentPickUpPile(int opponentNumber) {
        int pileSize = pile.getSize();

        for (int i = 0; i < pileSize; i++) {

            int currentCard = pile.getSize() - 1;
            //  String card = pile.getCardString(currentCard);
            // System.out.println(card);

            //move card to hand from deck, and add visualisation in hand panel
            opponentHand[opponentNumber].addCard(pile.getCard(currentCard));
            pile.removeCard(currentCard);

            createVisualOpponentCard(opponentNumber);
            setIcon(pileCards, "resources/pile.png");
            //reset text to initial text field
            initialiseText(textFeedback, "Card history: ");
            frame.revalidate();
            frame.repaint();

        }
    }

    static void playerPickUpPile() {
        int pileSize = pile.getSize();

        for (int i = 0; i < pileSize; i++) {

            int currentCard = pile.getSize() - 1;
            String cardName = pile.getCardString(currentCard);
            // System.out.println(card);

            //move card to hand from deck, and add visualisation in hand panel
            hand.addCard(pile.getCard(currentCard));
            pile.removeCard(currentCard);

            createCardButton(playerCards, cardName);
            setIcon(pileCards, "resources/pile.png");

            //reset text to initial text field
            initialiseText(textFeedback, "Card history: ");

            frame.revalidate();
            frame.repaint();

        }
    }

    //************************************************************************\\
    //                          ACTION LISTENER FUNCTIONS                     \\
    //************************************************************************\\
    //Display selected card value in textfield
    private static class PlayerCardSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cardName = e.getActionCommand();
            Object cardPressed = e.getSource();

            Card handCard = new Card(0);
            //identify hand Card location and copy card to temporary location
            int cardPosition = hand.locateCard(cardName);
            handCard.setCardNumber(hand.getCard(cardPosition).getCardNumber());

            if (Rules.playable(handCard, pile)) {
                playCard(cardPressed, handCard, cardPosition, cardName);
                updateFeedback(textFeedback, cardName);

                if (Rules.burnable(pile)) {
                    burnPile(pile, pileCards, frame);
                }
                //check to see current state of pile, deck, and hand
                //  System.out.printf("deck size = %d, hand size = %d, pile size = %d\n", deck.getSize(), hand.getSize(), pile.getSize());

              
                    opponentPlayCard(opponentVisual[0], 0);
                    opponentPlayCard(opponentVisual[1], 1);
                

            } else {

//                System.out.printf("Card not playable\n");
                String playableCardsList = "";
                int numPlayable = 0;

                for (int i = 0; i < hand.getSize(); i++) {
                    handCard.setCardNumber(hand.getCard(i).getCardNumber());
                    if (Rules.playable(handCard, pile)) {
                        playableCardsList += handCard.stringCard() + " ";

                        numPlayable++;
                    }
                }
                if (numPlayable > 0) {
                    System.out.printf("Playable cards: %s\n", playableCardsList);
                } else {
                    System.out.printf("No Playable cards.. player picking up pile\n");
                    playerPickUpPile();
                }

            }
//        try{
//         Thread.sleep(1000);
//
//               } catch (InterruptedException ex) {
//                  Logger.getLogger(PoopHeadGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        
        }

    }

    //add a deck card to hand
    private static class PickUpDeckCard implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //if deck still has cards in it then give one to player when it is selected
            //and add new card button to player hand for this card
            if (deck.getSize() > 1) {
                receiveDeckCard();

            } else {
                //get final card from deck
                receiveDeckCard();
                //deck has no more cards in it so remove the deck button
                deckCards.setVisible(false);
            }
            frame.revalidate();
            frame.repaint();

            //check to see current state of pile, deck, and hand
            System.out.printf("deck size = %d, hand size = %d, pile size = %d\n", deck.getSize(), hand.getSize(), pile.getSize());
        }
    }

    //************************************************************************\\
    //                            GAME PROPERTIES                             \\
    //************************************************************************\\
    private static final int NUM_PLAYERS = 2;
    public static CardGroup deck = new CardGroup();
    public static CardGroup hand = new CardGroup();
    public static CardGroup pile = new CardGroup();
    public static CardGroup[] opponentHand = new CardGroup[NUM_PLAYERS];

    //************************************************************************\\
    //                            GUI PROPERTIES                             \\
    //************************************************************************\\
    // Define different components, need to be defined here so that values are accessible to other functions?
    //needed / essential for desired functionality
    private static JFrame frame;  //window to hold everything
    private static JPanel communityCards; // panel to hold shared cards
    private static JLabel pileCards;      //pile representation added to communityCards
    private static JButton deckCards;     //deck representation added to communityCards
    private static JPanel playerCards;    // panel to hold player card buttons

    private static JPanel opponentPanel; //panel to hold computer's hands (up to 4 hands)
    private static JPanel[] opponentVisual = new JPanel[NUM_PLAYERS];

    //temporary text field to give feedback on buttons pressed.
    private static JTextField textFeedback; //textfeedback area

    // Define the action listeners
    private static ActionListener cardPlayed = new PlayerCardSelected();
    private static ActionListener getDeckCard = new PickUpDeckCard();

    //************************************************************************\\
    //----------------------       MAIN METHOD       -------------------------\\
    //************************************************************************\\
    public static void main(String[] args) {

        intialiseDeck(deck);

        //set up the gui components
        frame = new JFrame("Poophead");
        frame.setPreferredSize(new Dimension(1900, 800));

        //top section of JFrame window
        textFeedback = new JTextField();
        initialiseText(textFeedback, "Card history: ");

        //second section of JFrame window for deck and pile cards
        communityCards = new JPanel();
        initialisePile();
        initialiseDeck();
        initialiseCommunity(); //add pile and deck and set size / layout

        //third and final section of JFrame window for player's hand cards.
        playerCards = new JPanel();
        // Set the content panel
        //top, left, bottom, right border widths (applies to all attached panels)
        playerCards.setBorder(new EmptyBorder(5, 5, 5, 5));
        playerCards.setLayout(new GridLayout(1, 26, 1, 1));

        opponentPanel = new JPanel();
        opponentPanel.setLayout(new GridLayout(1, NUM_PLAYERS));
        opponentVisual[0] = new JPanel();
        opponentVisual[1] = new JPanel();
        opponentHand[0] = new CardGroup();
        opponentHand[1] = new CardGroup();

        //give each player 3 cards to start off with
        dealCards();

        // add all the components to the frame and set its size and layout
        initialiseFrame();
    }
}
