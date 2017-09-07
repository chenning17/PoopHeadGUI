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
        // first find out how big pile is in case we need to look at previous cards (if 8 on top of pile)
        int sizeOfPile = pile.getSize();

        if (pile.getSize() == 0) {
            //pile must have just been burned, picked up, or it is start of game
            //therefore can play any card on top of it.
            return true;
        } else if (handCard.getCardValue() == 2 || handCard.getCardValue() == 8 || handCard.getCardValue() == 10) {
            //check if player card is a 2, 8 or 10 as these cards can be played on anything
            return true;
        } else {

            //get top card from pile
            Card pileCard = new Card(0);
            pileCard.setCardNumber(pile.getCard(sizeOfPile - 1).getCardNumber());

            //set up card and checks for use in 8 comparisons
            Card previousValue = new Card(0);
            int cardsChecked = 0;
            int isEight = 0;

            if (pileCard.getCardValue() == 2) {
                //can play anything after a 2 has been played
                return true;
            } else {
                //if here means card was not a 2, and pile has not just been 
                //burned or picked up and it is not the start of the game

                //therefore, first check if pile card is an 8 as if it is need to find out what card value it represents
                if (pileCard.getCardValue() == 8) {

                    isEight = 1; //update now we have an 8

                    //can only ever have maximum of three 8s in a row as a 4th would have burned the pile
                    //therefore check card below it as long as size of pile is big enough to do so.
                    while (sizeOfPile > 1 && cardsChecked < 4 && isEight == 1) {
                        --sizeOfPile;
                        ++cardsChecked;
                        //set previous to card before in pile
                        previousValue.setCardNumber(pile.getCard(sizeOfPile - 1).getCardNumber());
                        if (previousValue.getCardValue() != 8) {
                            //if we have successfuly found a non 8 card then quit loop
                            isEight = 0;
                        }
                    }
                    //now update pileCard value to be that of preious card
                    pileCard.setCardNumber(previousValue.getCardNumber());

                }

                if (isEight == 1) {
                    //means only a few cards in pile and all of them are 8s so can play anything on top
                    return true;
                } else {
                    //pile card not an 8 or now not represented by an 8............
                    //check if pileCard is a 7 as need to play same or lower than it.
                    if (pileCard.getCardValue() == 7 && (handCard.getCardValue() <= 7)) {
                        //hand card is 7 or lower and pile card is a 7 so this is allowed
                        return true;
                    } else if (pileCard.getCardValue() == 7 && (handCard.getCardValue() > 7)) {
                        //not allowed to play higher than a 7 on a 7
                        return false;
                    } //if card is greater than or equal to pile card it is playable, otherwise it is not
                    else {
                        return handCard.getCardValue() >= pileCard.getCardValue();
                    }
                }
            }
        }
    }

    public static boolean burnable(CardGroup pile) {

        //get top card from pile
        int sizeOfPile = pile.getSize();
        Card pileCard = new Card(0);
        
        //make sure there is at least one card in the pile
        if(sizeOfPile <= 0){
            return false;
        }
        pileCard.setCardNumber(pile.getCard(sizeOfPile - 1).getCardNumber());

        Card testCard = new Card(0);
        int numberChecked = 0;
        int fourOfAKind = 1;

        if (pileCard.getCardValue() == 10) {
            //last card played was a 10 so burn pile
            return true;
        } else if (sizeOfPile >= 4) {
            //check if there was 4 of a kind as this would burn pile
            while (numberChecked < 3 && fourOfAKind == 1) {
                ++numberChecked;
                fourOfAKind = 0;
                //get previous card from pile
                testCard.setCardNumber(pile.getCard(sizeOfPile - (numberChecked + 1)).getCardNumber());

                if (testCard.getCardValue() == pileCard.getCardValue()) {
                    //previous card is of same value as the top card
                    System.out.printf("both same card value\n");
                    fourOfAKind = 1;
                }

            }
            return fourOfAKind == 1;

        } else {
            return false;
        }
    }

}
