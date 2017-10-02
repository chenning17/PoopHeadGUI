/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poopheadgui;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class is used to make groups of cards to be used as player's hands, the
 * deck and the pile in the game of poophead.
 *
 * @author Calum
 */
public class CardGroup {

    private List<Card> cardList = new ArrayList<>(0);

    public CardGroup() {
        //create empty card group object

    }

    //add a specific card to end of card group
    public void addCard(Card card) {
        cardList.add(card);

    }

    //remove a specific card from the card group
    public void removeCard(int cardPosition) {

        cardList.remove(cardPosition);
    }

    //return string representation of chosen card in card group
    public String getCardString(int cardPosition) {
        return cardList.get(cardPosition).stringCard();
    }

    //returns card object in chosen position in cardGroup
    public Card getCard(int cardPosition) {
        return cardList.get(cardPosition);
    }

    public Card getTopCard() {
        int sizeOfCardGroup = this.getSize();
        return cardList.get(sizeOfCardGroup - 1);
    }

    //shuffle the cards in the card group.
    public void shuffleCards() {
        Collections.shuffle(cardList);
    }

    public int getSize() {
        return cardList.size();
    }

    //locate card position in group from given input string identifier
    public int locateCard(String input) {

        int cardPosition;

        //identify hand Card via brute force method
        for (int i = 0; i < this.getSize(); i++) {
            if (input.equals(this.getCardString(i))) {
                //card has been found so move it to pile
                //handCard.setCardNumber(hand.getCard(i).getCardNumber());
                return cardPosition = i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    //return value an eight card at top of pile represents.
    public int getEightValue() {

        int cardsLeft = this.getSize() - 1; //size of cardgroup excluding top card
        int cardsChecked = 0;
        Card nextCard = new Card(7); //set card initially as an 8.

        while (cardsLeft > 1 && cardsChecked < 4 && nextCard.isEight()) {
            nextCard.setCardNumber(this.getCard(cardsLeft - 1).getCardNumber());

            cardsLeft--;
            cardsChecked++;
        }

        if (nextCard.isEight()) {
            //must have pile of only eights so set next card to a 2 so that 
            //anything can be played on top
            nextCard.setCardNumber(1);
        }

        return nextCard.getCardNumber();
    }

    public boolean isFourOfAKind() {

        Card topCard = this.getTopCard();
        Card comparisonCard = new Card(0);
        int cardsLeft = this.getSize();
        int numberChecked = 0;

        if (cardsLeft < 4) {
            return false;
        }

        while (numberChecked < 3) {
            //get next previous card from pile
            comparisonCard.setCardNumber(this.getCard(cardsLeft - (numberChecked + 1)).getCardNumber());
            if (comparisonCard.getCardValue() != topCard.getCardValue()) {
                return false;
            }
            numberChecked++;
        }
        return true;

    }
}
