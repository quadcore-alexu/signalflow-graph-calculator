# Signal Flow Graph Calculator

### The program designed by CSED, Faculty of Engineering, Alexandria University students.

## Main Features

#### Constructing signal flow chart

The program enables users to construct signal flow charts visually.

#### Detecting forward paths and loops

The program detects all forward paths and loops in the constructed signal flow chart; it also lists all the non-touching loops groups in the system.

#### Evaluating Transfer Function

The program evaluates the transfer function of the system with the constructed signal flow chart using mason’s formula, and shows the calculated deltas in the evaluation.

#### User-friendly interface

Easy interface to facilitate flow chart construction where the program is provided with an “Add node” button for adding nodes which you can be connected with edges by mouse drag and drop event.

The interface provides the user with a “Reset” button to enable him to reconstruct the signal flow chart thoroughly again.

The program shows the results in a dialogue with clear labels for each result.

#### Additional Feature: No critical overlap

The program prevents the overlap edges between two nodes, It draws the edge in arc style.

![](https://lh5.googleusercontent.com/Az_a7WqYZY0O0gS7dj05ta0slhXf6al1Hx2605ck5R4FCeQF7ANrwJSwb53enDbSHs_PcoAwE5JSVkOxfWeBC0xVjLXSW0vlimg7Rs5L1WNECjACLa2bHUN9fkcuy-45273iO6ws)

#### Additional Feature: Visual Tracing

The program enables the user to visually trace the listed forward paths and loops by clicking on the path label in a user friendly way.
![](https://lh4.googleusercontent.com/08PeE1gjeQ8-Fj3Zb8qw1mRnj6xz-zyKvCl3IU7qrNtMvhy1Ry9MeIdbH8lheUtGbOKG_E5etUInDCRicc1i-nOxm55eUII8rl2A0zokIri7ClAGrype8xF_Z_XQqkiSUqmzAs3d)

  
  
  
![](https://lh6.googleusercontent.com/8P9PMzFbLD9OYOEBlSk66HZSb3xJcl0397ogFr_UFsXc6JhzTQ08zeCIx_pf9bhlEhy2qyv1orkqNfIJU6_T3_iBOqLOTckbifFcMHy_TJOiBi6Z2k89KjxxnGvLxLH77zUb1GhA)![](https://lh5.googleusercontent.com/Yt_xybY_9EF2jBG0DmM4aB3lye9FJNorvx9j2mwoSqfe6EOVS_LoPP95yhioEcNgcerTUyexuHcoO1e3Ms2_GBVGW3LNySaWai4SpEmRC537pe2ToMg1urob2yrlzuL_-_0JK2-Q)![](https://lh4.googleusercontent.com/s5obhO_Soie8qXkM90y1_55bmVT07mpvxZWbE3OCI6KBPXxAlUKGqmFptB_kyp_9FDzeqPHilnBQsdXEUVu3VFMh6JDSKK1_jPCYng95e8UMZJpAClnhYZ_1QsSiS9BvOgvgrkkG)

## Data Structure

-   #### Directed Graph:  
    - It simplifies mapping between the given signal-flow-graph in GUI and back-end.  
    - It's self implemented in the application using node class (which holds edges) and edge class (which holds gains) in order to ease traversing through the graph and detect loops and paths.  
      
    
-   #### HashMap:  
	 In front-end:
-   It is used to map between an array of nodes with its corresponding cells and node ID as a key to ease accessing cells.
    

	 In back-end:

-    In SignalFIowGraph class, it is used to map between all n groups of non-touching loops and its group number .
    
-    In DeltaCalculator class, It is used to hold deltas for each path with a path as a key.  
      
    

-   ### ArrayList:  
    - It is the most used data structure in storing various data, As data could be added dynamically whenever needed.  
  
## Main modules

The application is written in java and is divided into two main modules, the model and the view (using swing). The view is concerned with enabling users to draw the graph, validating, and then mapping the drawn graph to the model in order to then view the results. Following are the main modules of the model.

1.  Signal Flow Graph: is the main class that the GUI deals with. (Facade design pattern).
    
2.  Node Visitor: an instance of this class can get all the paths and loops in a connected directed graph.
    
3.  Non Touching Loop Calculator: given a list of all loops (using node visitor), this module can handle getting the non-touching loops with all its nasty details. That is, it gets all two, three, four, five,... non-touching loops.
    
4.  Delta Calculator: given the list of nodes and paths , this module computes:
    

	-   The delta by first computing the adjacency matrix for the graph then its augmented adjacency matrix(see algorithms below).
    
	-   Delta i.
    
	-   Transfer function since this module has all the paths and deltas.
## Algorithms

1- Modified dfs: this algorithm is used to detect loops and paths in a graph

Algorithm:

	visit (Node node)

	open node //mark node as open

	add node to currentPath

	if node is outputNode: //we found a path

		create path

	for each child:

		if child is not open :

			visit(child)

		else if open first time // to prevent multiple detection of same loop

			create loop

	close node

	mark as wasClosed


2- ConstructAdjacencyMatrix:

Algorithm:
	
	adjMatrix [noOfNodes] [noOfNodes]

	constructAdjMatrix ( )

	For all node :

		currentColumnIndex = node.id

		For all edges:

			currentRowIndex = edge.endNode.id

			adjMatrix [currentRowIndex] [currentColumnIndex] = edge.gain

	return adjMatrix

  
  

3- getAugmentedMatrix:

AugmentedAdjMatrix is I-𝜆*adjMatrix where 𝝀=1

Algorithm:

	getAugmentedMatrix ()

	Matrix identityMat= Matrix.Identity (noOfNodes , noOfNodes)

	Matrix augmentedAdjMatrix = identityMat-adjMatrix

	return augmentedAdjMatrix

  

4- getDelta

Algorithm:

	getDelta ( )

	return augmentedAdjMatrix.determinant

# Sample runs  
![](https://lh5.googleusercontent.com/akkRWz8bvaJ0tW6FG9d1kA5zpbyrfGqufeq_zHR0SiipNP94jq9ZD8h1eJcYfCffL319TCYQJfI6dNF92PRZDs4nAct3DmND0APiZLTawHnmjrb28890P8MB_M1DHCKe61AMjfCO)![](https://lh4.googleusercontent.com/51b-xdkfzlIsKgwW_or6EPf6sK0DXicP9B5u0DnThNe2-0O04D5ygPn3JOBIgRPP-cBAUqjdq0Chbc-F2jOG2ehp2ksu8BkXzWDlpL2fCPrYZ8RhFanjGrFAnPAswBGSYuHYhFbh)![](https://lh4.googleusercontent.com/1gT5184DbgAMFX3bm-55f4BBkNsEtfxjm5na-0rxVVtLNGFgzfkpQU06269fu-x9B2OgGkwy9LQz2clRLlvhyKlswWhtBasnoqxK_iyd52UfJHjUDup93lzvKyknCAIEGF3zjCUb)![](https://lh5.googleusercontent.com/JDEqAhzuLnDPOUROi197Hk2qAeQaRB0OzagySrnfD132jtfIq6IseT7bI4ZDSYvNlshqMwbK8CkxJj4zWY8SlZexASod96Y-CFxzp-izVLBLM8gUuO6D8fTOXvnckswIxevXDgU8)![](https://lh3.googleusercontent.com/4fhKpHcYtQp-8npseH0LPWneSsZxP_Gx7CgIcfV2JLf0mDS9pt4aQcLmNTxdZnlN2Kei_02cfcaprMxyrWXGfyK-d-gc9DHxyDLqXpL2Ov_OUfWPMlZOa2PQsdcKAOf4pTAinfJv)![](https://lh6.googleusercontent.com/hzz8B7CIZ18eK1VwYabxHK8JuOmFGApEVECsQcH02VegOuJxas0Na7iRs3sgCV33g0ZnUeDYuMKwjzlMQsGZK0tziNUoZ3KSg7eXKn8gSTP8tkC2oQEfGJ1bVdUQxIhYKDU9vGV6)![](https://lh3.googleusercontent.com/u4BZVZhML67LNpQzqwE-a-L1KBVixh4O-zQsnL9e-Cn2zRRQ__duCifDJTnbbYngz_1op_ypvbOpxJ6jTtoOgTVSllob4LC4yeUMiei5qIOaoj0J_w5ALeDgAkzyZyEUcKstQp0X)

![](https://lh3.googleusercontent.com/zi7RMgRi25tg2Vjn-DgGSG1odJ3yzdWDK51Ony6InTkeupKIJy_HP4O5elMbnwE1J8ZGvraUy5JUpld890DwjiVEAB-xadV9piuo8nmaBPYlQITqFQy6eoBU_o5hsJo4JlHX_wAb)  
  

## Simple user guide

![](https://lh3.googleusercontent.com/ujDtCzTVRQuNmEhvVwvTN2UoHrLkevJwaHdPqIK6vQ8dRrJiTuoWAmqoyd0Vkw6kH8t6-tMTbWSVqlISecAfVYhRV-Er4Znn5FBO4u2QonZCL4uwjDSwPP1q0Im6YL7VH2d4b2Om)

1.  On opening you will find the start and end node given, continue to add the rest of nodes and branches between them.
    

![](https://lh6.googleusercontent.com/ILLTnvn2yCteJ-cGRRgZq1yXux-AhfNcpwnzZOG6fDIE9yJjgSvdK5pBCfT1HFXIXmUdm4l9L4xVAxwTLfVCrGr6pcul-5EcWylUR6UA9-0tf2Hr076TbITcsheNbWCVVJNzTDc0)

2.  Add nodes using the add node button then add edges between nodes to draw your desired graph.
    

![](https://lh6.googleusercontent.com/cMT4oY9ktLoyT98NHIfNQ9soW7oSIc8LM0EVTmmS7kDF2lS5HFIvfTvCw-R13r3CTKU4aXyjp-RFcakFt8A9wQrPT-Tujt3vOcK4FDca1SELpL9TDc0u34SD8ty6wuWPEGjr-AVe)![](https://lh5.googleusercontent.com/mAd4rEiblfhOwcvHuVDEwj8oJnwDg92Ogi-WJdzAnfldMuicSQNH2jQTyQOMFIH-zpgMhSacWLdLjX3Fw53FGmX7RMa_mnXV-h45wIrqxnJktaEnAhV799iYlcGC5qjc-7Y1OFX9)

3.  You can make a self loop by making the start node the end node so the edge is coming from and to the same node.
    
4.  The gain of each edge is set to 1 by default, double click on the number to change the gain of the branch.
    

  

5.  After drawing the graph and entering gains of each branch, click on the calculate button to view all forward paths, loops, non touching loops, deltas and the overall system transfer function.
    

![](https://lh5.googleusercontent.com/BbHwQnKX3DMDyrC9BpDNhvz7h31gSSH8D805xNGHSj2cO5mvEV_lEhpxqyJAd7lXnUPjRtxcSjJzLR8WtqmzTemSojV7ZC8COKfF8yBlqkkvkRBPHdlQbHt8sKKlgXy1nM-Td_7c)

  
  
  
  

6.  On showing the results, you can click on the path you want to identify it on the graph with a different color, you can do the same thing with loops.![](https://lh6.googleusercontent.com/RJjDgDMYkEUxiVOXZR_rw_eSzoxks9Tbs0wxIgY3OSXLY1B5x9rh4siOmvW0uDMhKPqOiMDHDleO5q4XrhQrWs32DMZabJCGNEK8cLfFkIHSwjBA8PsyD5XjOi_2efiFghQEFDPn)
    

  

7.  Finally, you can reset using the reset button and start drawing the graph all over again.
    

![](https://lh6.googleusercontent.com/lYhJwiWzGrQI7_uaNCF26ccixSjCKZnvkMKB3oPF2Rz_R2MMTpv19uAjC7yQ2iiamUE5fzi2dEwOcS_6Fd248cBs34CQ8LLwe63WvbSa_49qidvISaEWBqB78YqnNWGTXZJ6zTfO)
