Input:
You will be given a text input file. First line of this file represents the number of test cases. The next line will be the beginning of the 1st test case. Each test case ends with an empty line. Each test case consists of the following lines:
􏰀 <task> algorithm that you are supposed to use for this case
􏰀 <source> name of the source node
􏰀 <destinations> names of the destination nodes
􏰀 <middle nodes> names of the middle nodes
􏰀 <#pipes> represents the number of pipes
􏰀 <graph> represents start-end nodes, lengths and off-times of pipes
􏰀 <start-time> the time when water will start flowing

Output:
For each test-case, one per line, report the name of the destination node (case-sensitive, uppercase only) where water reached first and also the time that it reached that node. These two fields should be separated by a space. If no path was found to any of the destination node, i.e. none of the destination nodes can be reached, print “None”.
