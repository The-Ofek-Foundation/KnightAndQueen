# KnightAndQueen
Counts the number of Eight Queen Solutions. This algorithm can count the number of 15 by 15 solutions in less than 7 seconds (on my i7 laptop).
The "Quites" are Queen Knights, which should run considerably faster.


### Usage

java EightQueens <board width> <multithreading depth (1 or 2)> <include additional value for quites>
java Quites <board width>

Note that depth 1 for multithreading is generally better (unless you have many cores)