package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airline> airlines;
	
	private FlightDAO dao;
	
	private List<Airport> airports;
	
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	
	public Model(){
		dao = new FlightDAO();
	}

	public List<Airline> getAirlines() {

		if(this.airlines == null)
			airlines = dao.listAllAirlines();
		
		return this.airlines;
	}
	
	private List<Airport> getAllAirports(){
		
		if(this.airports == null)
			airports = dao.getAllAirports();
		
		return this.airports;
	}
	
	public void creaGrafo(Airline airline){
		
		graph = new SimpleDirectedWeightedGraph <Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, this.getAllAirports());
		
		Map <Integer, Airport> map = new HashMap <Integer, Airport>();
		
		for(Airport a : graph.vertexSet())
			map.put(a.getAirportId(),a);
		
		for(Route r : dao.listAllRoutesForAirline(airline)){
			
			Airport source = map.get(r.getSourceAirportId());
			Airport destination = map.get(r.getDestinationAirportId());
			
			DefaultWeightedEdge e = graph.addEdge(source, destination );
			
			if(e != null){
				LatLng c1 = new LatLng(source.getLatitude(),source.getLongitude());
				LatLng c2 = new LatLng(destination.getLatitude(),destination.getLongitude());
				
				graph.setEdgeWeight(e, LatLngTool.distance(c1, c2, LengthUnit.KILOMETER));
			}
		}
		
		System.out.println(graph);
		
	}

	public List<Airport> getServiti() {

		List<Airport> serviti = new ArrayList<Airport>();
		
		for(Airport a : graph.vertexSet())
			if(graph.inDegreeOf(a) > 0 || graph.outDegreeOf(a) > 0)
				serviti.add(a);
		
		return serviti;
	}
	
	
	
	public List<AirportAndDistance> getRaggiungibili(Airport airport){
		
		List<AirportAndDistance> result = new ArrayList<AirportAndDistance>();
		
		LatLng c1 = new LatLng(airport.getLatitude(),airport.getLongitude());
		
		List<AirportAndDistance> visitati = new ArrayList<AirportAndDistance>();
		
		findRaggiungibili(airport,c1,visitati);
		
		for(AirportAndDistance ad : visitati)
			if(!ad.getAirport().equals(airport))
				result.add(ad);
				
		Collections.sort(result);
		
		return result;
	}
	
	public void findRaggiungibili(Airport airport, LatLng c1, List<AirportAndDistance> visitati) {
		
		for(DefaultWeightedEdge e : graph.outgoingEdgesOf(airport)){
			
			Airport a = graph.getEdgeTarget(e);
			
			LatLng c2 = new LatLng(a.getLatitude() , a.getLongitude());
			
			double distance = LatLngTool.distance(c1, c2, LengthUnit.KILOMETER);
			
			AirportAndDistance raggiungibile = new AirportAndDistance(a,distance);
			
			if(!visitati.contains(raggiungibile)){
				visitati.add(raggiungibile);
			}
			else
				continue;
			findRaggiungibili(a, c1, visitati);
		}
		
		return ;
	}

}
