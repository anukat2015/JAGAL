package de.uni.freiburg.iig.telematik.jagal.graph.exception;

import de.uni.freiburg.iig.telematik.jagal.graph.Edge;
import de.uni.freiburg.iig.telematik.jagal.graph.Vertex;
import de.uni.freiburg.iig.telematik.jagal.graph.abstr.AbstractGraph;



public class VertexNotFoundException extends GraphException {
	
	private static final long serialVersionUID = 1L;
	private String messagePart = " does not contain vertex ";
	private String vertex;

	public <V extends Vertex<U>, E extends Edge<V>, U> VertexNotFoundException(V vertex, AbstractGraph<V, E, U> graph){
		super(graph.getName());
		this.vertex = vertex.toString();
	}
	
	public <V extends Vertex<U>, E extends Edge<V>, U> VertexNotFoundException(V vertex, String vertexDescription, AbstractGraph<V, E, U> graph){
		super(graph.getName());
		this.vertex = vertex.toString();
		messagePart = " does not contain "+vertexDescription+" ";
	}
	
	public String getMessage(){
		return getGraph()+messagePart+getVertex();
	}
	
	public String getVertex(){
		return vertex;
	}
}