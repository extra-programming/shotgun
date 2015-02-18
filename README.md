#Shotgun Sequencing!

---
###Description:
A project to sequence DNA

---
###Goals:
####Completed:
* Start project
* Create a README
* Create DNA to sequnence

####In Progress:
* Fake the process of cutting-up and sequencing short strips of DNA
  * The sequencing error rate should be significantly higher near the ends of the segments
* Make tests
  *  How do we test random stuff? [This article](http://c2.com/cgi/wiki?UnitTestingRandomness) looks like a good place to start...

####Short Term Plans:
* Think about animating (at least the recombining of long sequences), probably color-coded. Maybe long matching sequences would get highlighted before being dragged together. Maybe we can learn from the [animated sorters](http://commons.wikimedia.org/wiki/Category:Animations_of_sort_algorithms). Note: google search [shotgun sequencing animation](http://www.google.com/search?client=safari&rls=en&q=shotgun+sequencing+animation&ie=UTF-8&oe=UTF-8) finds many potential videos and tutorials. (Including DNA Learning Center
has [shotgun sequencing video](http://www.dnalc.org/view/15538-Genome-Sequencing-Shotgun-technique-3D-animation-with-no-audio.html) from Cold Spring Harbor Laboratory.



####Long Term Plans:
* Re-assemble the sequenced genome

####Recommended reading
* [Jim Kent's GigAssembler](http://www.ncbi.nlm.nih.gov/pmc/articles/PMC311095/)
* http://en.wikipedia.org/wiki/Shotgun_sequencing (also see 
  http://en.wikipedia.org/wiki/DNA_sequencing_theory
  http://en.wikipedia.org/wiki/DNA_sequencing_theory#Pairwise_end-sequencing
  http://en.wikipedia.org/wiki/Sanger_sequencing ) (ps: note, "Chain Termination Method" produces reads with lengths of approx .5 to 1 kb)
* NASA's code standards (when life depends upon it) http://pixelscommander.com/wp-content/uploads/2014/12/P10.pdf
* All kinds of [interactive animated algorithms] (http://www.cs.usfca.edu/~galles/visualization/Algorithms.html) (Flash and Java), including stacks, queues, binary search trees, sorters, etc. Better than the wikipedia animations because we can control speed, step forward &amp; backward, etc.  http://www.cs.usfca.edu/~galles/visualization/Algorithms.html
  * Add this link to wikipedia if it is not there!

####Questions for further research
* Is our random dna really random in the right way? How do we test that our fake dna has typical distribution of the standard base pairs?
* Standards for difference measurement and error count between reads of a sequence (From nature article, "Finishing the euchromatic sequence of the human genome"
accessed 2015 feb 18:
http://www.nature.com/nature/journal/v431/n7011/full/nature03001.html
"Figure 2: Assessment of potential errors by analysis of BAC overlaps.
a, Single-base differences between overlapping finished BAC clones (with ≥5 kb overlap). The number of single-base differences in overlaps for clones from the same library and from different libraries is plotted. The results are consistent with half of the clones from the same library representing identical underlying DNA sequence with low error rate, and half representing different haplotypes as expected. b, Insertion/deletion (indel) differences between overlapping clones. The number of indels per Mb for a given size range is compared for clones with no single-base mismatches (presumed to be derived from the same haploid source) and >3 single-base mismatches (presumed to be derived from different haploid sources). Indels in the former class primarily represent errors in finished sequence; they occur at ~20-fold lower frequency (inset) than indels in the latter class, which primarily represent polymorphic differences."
* Do "restriction sites" break at particular motifs?
