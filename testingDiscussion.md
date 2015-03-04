# Thoughts about our unit tests

## Things that our random dna generator might do poorly but we don't know how to test for:
* Might not really be random...:
  * Might spit out all 'C's repeatedly, every time. WLOG T or G or A.
  * Might spit out 'ACTG' over and over again, every time. Or some other pattern.
* Might not have the patterns or distributions of real DNA. Aren't there stats packages we could use for this?


## Things that our random dna slicer might do poorly but we don't know how to test for:
* Might not slice the way a real life slicer does:
  * Might always slice a sequence at the same places so we get no overlaps. This makes re-sequencing impossible.


## Our simulation of mis-reading ("corruptTheSequences( )" and "corruptTheSequence( )") might not get the kinds of mistakes in reading that real sequencing equipment makes.
