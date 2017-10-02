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
public class CardGroup{

        
    private List<Card> cardList = new ArrayList<>(0);

    public CardGroup() {
        //create empty card group object
        
    }

    //add a specific card to end of card group
    public void addCard(Card card){
        cardList.add(card);
        
    }
    
    //remove a specific card from the card group
    public void removeCard(int cardPosition){
        
        cardList.remove(cardPosition);
    }
    
    //return string representation of chosen card in card group
    public String getCardString(int cardPosition){
        return cardList.get(cardPosition).stringCard();
    }
    
    //returns card object in chosen position in cardGroup
    public Card getCard(int cardPosition){
        return cardList.get(cardPosition);
    }
    
    //shuffle the cards in the card group.
    public void shuffleCards(){
        Collections.shuffle(cardList);
    }
    
    public int getSize(){
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
    
}
