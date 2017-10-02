# PoopHeadGUI

A fun card game based on the game s***head (rules vary for different versions of the game). 


Currently have set up a basic one player GUI that allows player to pick up cards from the deck
and play them onto the pile according to the rules:
        
        -> 2 resets 
        
        -> 7 or lower
        
        -> 8 see-through
        
        -> 10 burn deck
        
        -> Ace is high
        
        -> 4 of a kind burns deck

        -> cards must be played with equal or greater value than previously 
           played card unless altered by the other rules above. If no card can 
           be played, (must pick up the pile. - not applied picking up pile yet)

        (-> J reverse direction - not applied yet since only one player at the minute)
        
        (-> must have 3 cards in hand at all times while original deck has cards
            - not applied yet)
        
        (-> dealt three face down cards and 3 face up cards, can swap face up 
            ones with ones originally dealt to hand - not applied yet)
        
        (-> can choose to play more than one card at a time if same value - not 
            applied yet / only one player)
        

TODO:

-> ***clean up code so it is easier to read / work with*** 
    -> refactored original code but since adding other player functionality has 
       got out of hand again so will need to do this again

-> properly implement having multiple players - IN PROGRESS - eventually add choice
   of how many, fixed at 2 opponents at the moment
    -> fix opponent playing 10 bug, does not burn pile... - DONE
    -> separate GUI from code properly so that pauses can be added as it is too 
       quick at the moment to see opponent's played cards.

-> implement Jack change of direction rule

-> implement playing more than one card at a time rule

-> add in three visible cards and three unknown for each player for the end of 
   the game

-> fix other player's positioning in window (currently swap about depending on 
   number of cards held)