/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poopheadgui;

/**
 * Class to invoke the correct rules for the poophead game
 *
 * @author Calum
 */
public class Rules {

    public static boolean playable(Card handCard, CardGroup pile) {
        //will return true if it is possible to play the chosen hand card onto the pile
        //returns false if not
        /*
        * -> cards must be played of same or higher value
        * - 2 resets pile
        * -> 7 must play a 7 or lower
        * -> 8 is see-through, acts like card before it
        * - 10 burn pile, so all cards are removed from it
        * -> J reverse direction of play
        * - Ace is high
        * 
         */

        if (pile.isEmpty() || handCard.isAlwaysPlayable()) {
            return true;
        }

        Card topPileCard = pile.getTopCard();

        if (topPileCard.isTwo()) {
            return true; //can play anything after a 2 has been played
        }

        if (topPileCard.isEight()) {
            topPileCard.setCardNumber(pile.getEightValue());
        }

        //pile card is not an 8 or is now not represented by an 8............
        if (topPileCard.isSeven()) {
            return handCard.isSevenOrLower();
        }

        //if card is greater than or equal to pile card it is playable, otherwise it is not
        return handCard.getCardValue() >= topPileCard.getCardValue();
    }

    
    public static boolean burnable(CardGroup pile) {
        /* pile should be burnt (cards in it binned) if a 10 is played onto it or
         * if four cards of the same value i a row are played onto it.
        */
        
        if (pile.isEmpty()) {
            return false;
        }

        Card topPileCard = pile.getTopCard();

        if (topPileCard.isTen()) {
            return true;
        }

        return pile.isFourOfAKind();

    }

    //function to check if hand has at least 3 cards while deck still has cards available
    //if not enough cards, automatically pick up from deck until hand contains at least 3 or deck runs out.
    public static boolean enoughCards(CardGroup hand, CardGroup deck) {

        if (hand.getSize() >= 3) {
            return true;
        } else {
            return !(hand.getSize() < 3 && deck.getSize() >= 1); //just checking for one card at the moment as can only play one card at a time just now
        }        //must be less than 3 cards but deck has no cards left so this is okay

    }

}
