Game has multiple stages. Each stage will fetch and save different type of data and feed them to the bot
---What files are stored---


---The stages---
1. the vulture/mouse cards gets shown to the players
2. each player selects one of their cards, but they remain hidden for the other players
3. everyone reveals their card value
4. the 'game control' removes the edge cases and picks the highest/lowest card player
5. the points are given to the winning player
6. the 'game control' removes all played cards from the game
7. small break and the loop repeats

---Edge cases---
Everyone has the same card value
Two people have the highest/lowest card value

---Edge cases with multiple bots---
caught in a loop where every bots plays the same card value over and over again
