# KnightAndQueen
Counts the number of Eight Queen Solutions. This algorithm can count the number of 15 by 15 solutions in less than 7 seconds (on my i7 laptop).
The "Quites" are Queen Knights, which should run considerably faster.


### Usage

javac *.java

java EightQueens <board width> <multithreading depth (1 or 2)> <include any additional value for quites>

java EightQueensUnoriginal <board width> <multithreading depth (1 or 2)>

Deprecated:

java Quites <board width>

Note that depth 1 for multithreading is generally better (unless you have many cores)

### What is Eight Queens Unoriginal?

There is another method of solving the eight queens problem that I found online [here](http://introcs.cs.princeton.edu/java/23recursion/Queens.java.html) that is only slightly slower than mines. I implemented my multithreading on that one too, and lo and behold, it's only slightly slower than my multithreading (7.368245 vs 6.716427 seconds for 15 by 15, 48.806573 vs 43.884847 for 16 by 16).

Be sure to check out the original code (instead of copying my implementation) if you are interested!

Which is better?

As the unbiased creator of my own version, I'm gonna say that my version is better because it runs significantly faster on much larger boards and it also implements the Eight Knight Queens solution, but you can decide for yourself.
