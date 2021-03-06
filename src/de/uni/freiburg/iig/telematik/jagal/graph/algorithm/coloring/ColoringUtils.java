package de.uni.freiburg.iig.telematik.jagal.graph.algorithm.coloring;


import java.util.HashSet;
import java.util.Set;

import de.invation.code.toval.validate.ParameterException;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.jagal.graph.Edge;
import de.uni.freiburg.iig.telematik.jagal.graph.Graph;
import de.uni.freiburg.iig.telematik.jagal.graph.Vertex;
import de.uni.freiburg.iig.telematik.jagal.graph.abstr.AbstractGraph;
import de.uni.freiburg.iig.telematik.jagal.graph.exception.GraphException;


public class ColoringUtils {
	
	public static <V extends Vertex<U>, E extends Edge<V>, U> Coloring<V> naiveColoring(AbstractGraph<V, E, U> graph) {
		Validate.notNull(graph);
		Coloring<V> coloring = new Coloring<>();
		Set<Integer> neighborColors = new HashSet<>();
		for(V vertex: graph.getVertices()){
			neighborColors.clear();
			try {
				for(V neighbor: graph.getNeighbors(vertex.getName())){
					if(coloring.isColored(neighbor)){
						neighborColors.add(coloring.getColor(neighbor));
					}
				}
			} catch (GraphException | ParameterException e) {
				// Cannot hapen, since we only use graph vertexes.
				throw new RuntimeException(e);
			}
			Integer vertexColor = 1;
			while(neighborColors.contains(vertexColor)){
				vertexColor++;
			}
			coloring.setColor(vertex, vertexColor);
		}
		return coloring;
	}
	
	public static <V extends Vertex<U>, E extends Edge<V>, U> Coloring<U> getElementColoring(AbstractGraph<V, E, U> graph, Coloring<V> vertexColoring) {
		Validate.notNull(graph);
		Coloring<U> elementColoring = new Coloring<>();
		for(V vertex: graph.getVertices()){
			elementColoring.setColor(vertex.getElement(), vertexColoring.getColor(vertex));
		}
		return elementColoring;
	}
	
	/**
	 * Determines the maximum clique of the given graph.<br>
	 * WARNING: Extreme bad runtime, use only for small graphs.
         * @param <V>
         * @param <E>
         * @param <U>
	 * @param graph
         * @return 
	 */
	public static <V extends Vertex<U>, E extends Edge<V>, U> Set<V> maxClique(AbstractGraph<V, E, U> graph){
		Set<V> vertexes = new HashSet<>();
		Set<V> biggestCliqueSoFar = new HashSet<>();
		for(V testVertex: graph.getVertices()){
			// Build the biggest clique containing the current vertex.
			vertexes.addAll(graph.getVertices());
			vertexes.remove(testVertex);
			
			Set<V> maxClique = new HashSet<>();
			for(V vertex: vertexes){
				boolean isCliqueVertex = true;
				// Check if the given vertex is connected to all vertexes in the clique
				for(V cliqueVertex: maxClique){
					try {
						if(!graph.getChildren(cliqueVertex.getName()).contains(vertex) && !graph.getParents(cliqueVertex.getName()).contains(vertex)){
							// Vertex cannot belong to the clique
							isCliqueVertex = false;
							break;
						}
					} catch (GraphException e) {
						// Cannot happen, since we only use graph vertexes
						throw new RuntimeException(e);
					}
				}
				if(isCliqueVertex){
					maxClique.add(vertex);
				}
			}
			if(maxClique.size() > biggestCliqueSoFar.size()){
				biggestCliqueSoFar = maxClique;
			}
		}
		return biggestCliqueSoFar;
	}
	
	public static void main(String[] args) throws Exception {
		Graph<String> g = new Graph<>();
		g.addVertex("1");
		g.addVertex("2");
		g.addVertex("3");
		g.addVertex("4");
		g.addVertex("5");
		g.addVertex("6");
		g.addEdge("1", "2");
		g.addEdge("1", "5");
		g.addEdge("2", "5");
		g.addEdge("2", "3");
		g.addEdge("5", "4");
		g.addEdge("3", "4");
		g.addEdge("4", "6");
		System.out.println(maxClique(g));
	}

}
