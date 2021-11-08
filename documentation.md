Game has multiple stages. Each stage will fetch and save different type of data and feed them to the bot
---What files are stored---


---The stages---
1. a new 'center card is shown to both players'
2. each player selects one of their cards, but they remain hidden for the other players
3. everyone reveals their card (with their value)
4. the 'game control' removes the edge cases and picks the highest/lowest card player
5. the card (and so the points) are given to the winning player
6. the 'game control' removes all played cards from the game
7. small break and the loop repeats

---Edge cases---
Everyone has the same card value
Two people have the highest/lowest card value

---Edge cases with multiple bots---
caught in a loop where every bots plays the same card value over and over again


--- Rules according to https://www.youtube.com/watch?v=qifupF6pwEw ---

Ziel des Spiels?
Alle Erdmännchen-Karten einsammeln, und bloß nicht die Geier-Karten einsammeln

Was bekommt jeder Spieler?
15 Zahlenkarten mit den Werten 1-15. Jeder Spieler hat seinen eigenen farbigen markierten Stack Karten

Vorbereitung?
Die Erdmännchen- und Geier-Karten werden gemischt und als verdeckter Stapel hingelegt

Runden-Rotation
Eine Karte vom Erdmännchen-Geier-Stapel wird aufgedeckt. Um diese Karte wird gespielt
Jetzt legt jeder Spieler verdeckt eine seiner Zahlenkarten vor sich.
Dann drehen alle ihre um.
Ist die Karte ein Erdmännchen, bekommt diese die höchste Zahlen-Karte.
Ist die Karte ein Geier, bekommt diese die niedrigste Zahlen-Karte.
Alle gelegeten Zahlen-Karten werden eliminiert (offen abgelegt?)
-> Runde rotiert

End-Bedingung
Erdmännchen-Geier-Stapel == 0.
Gewinner ist der, der die meisten Punkte ergattert hat


Rand-Fälle
Haben 2 relevante Karten denselben Wert, werden diese Karten für die Bewertung nicht beachtet
Alle Karten sind identisch: Dann wird die Punktekarte nicht verteilt, sondern bleibt in der Mitte liegen.
In der nächsten Runde wird eine weitere Punktekarte aufgedeckt und dazugelegt. Dann zählt ihr die Werte aller offenen
Punktekarten zusammen. Ist die Summe Null oder höher, spielt ihr darum wie um ein Erdmännchen, ist sie kleiner als Null
wie um einen Geier. Wer die Runde gewinnt, bekommt alle offenen Punktekarten. Ausnahme: In der letzten Runde wird
die Punktekarte stattdessen einfach nicht verteilt.