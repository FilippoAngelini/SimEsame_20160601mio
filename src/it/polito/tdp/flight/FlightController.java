package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportAndDistance;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    
    public void setModel(Model model){
    	this.model = model;
    	this.boxAirline.getItems().addAll(model.getAirlines());
    }

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	
    	Airport airport = boxAirport.getValue();
    	
    	if(airport == null){
    		txtResult.appendText("Selezionare un aeroporto\n");
    		return;
    	}
    	
    	List<AirportAndDistance> raggiungibili = model.getRaggiungibili(airport);
    	
    	txtResult.clear();
    	
    	txtResult.appendText("Aeroporti raggiungibili: \n");
    	
    	for(AirportAndDistance ad : raggiungibili)
    		txtResult.appendText(ad.getAirport().toString() + " Distanza: " + String.format("%.3f", ad.getDistance()) + " KM\n");

    }

    @FXML
    void doServiti(ActionEvent event) {
    	
    	Airline airline = boxAirline.getValue();
    	
    	if(airline == null){
    		txtResult.appendText("Selezionare una compagnia aerea\n");
    		return;
    	}
    	
    	model.creaGrafo(airline);
    	
    	List <Airport> airports = model.getServiti();
    	
    	txtResult.clear();
    	
    	txtResult.appendText("Aeroporti serviti dalla compagnia: \n");
    	
    	for(Airport a : airports)
    		txtResult.appendText(a.toString() + "\n");
    	
    	boxAirport.getItems().clear();
    	
    	boxAirport.getItems().addAll(airports);

    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }
}
