check for all integers, arrays
firstUserVictoryValue = 0
secondUserVictoryValue = 0
each user is an object with "int VictoryValue" and "int[] myCards"
int [] centerCards
---------------------------------------------------------------------
welcome message
while centerCards is not empty
    
    --- centralCards ---
    reveal random card value (validate)
    store as int currentCenterCard
    remove this card from the stack

    --- get user input --- (allow for quit!)
    prompt the first user to select their card
    validate input
    get the value of the chosen card -> int firstUserCard
    remove this card from his stack

    prompt the second user to select their card
    validate input
    get the value of the chosen card -> int secondUserCard
    remove this card from his stack

    --- winning condition checker --- neg/pos
    if current_centralCard value is negative
        if firstUserCard > secondUserCard
            add current_centralCard to secondUserVictoryValue
        else
        add current_centralCard to firstUserVictoryValue
    else
        if firstUserCard > secondUserCard
           add current_centralCard to firstUserVictoryValue
        else
            add current_centralCard to secondUserVictoryValue
    
    round overview message
    <- looping ->

declare winner message
end program