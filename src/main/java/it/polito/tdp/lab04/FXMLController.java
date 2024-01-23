package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private Button btnIscriviti;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<Corso> cmbCorsi;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCercaCorsi(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	Corso corso = this.cmbCorsi.getValue();
    	
    	if(!matricola.isEmpty()) {
    		int matricolaNum = 0;
        	
        	try {
        		matricolaNum = Integer.parseInt(matricola);
        	} catch(NumberFormatException e) {
        		this.txtResult.setText("Il valore inserito non è di tipo intero.");
        		return;
        	}
        	
        	Studente studente = this.model.getStudenteFromMatricola(matricolaNum);
        	
        	//se lo studente non è nullo, allora esiste
        	if(studente!=null) {
        		
        		List<Corso> corsiStudente = this.model.getCorsiStudente(matricolaNum);
        		
        		//se la lista di corsi dello studente è vuota, allora lo studente non è iscritto a nessun corso
        		if(corsiStudente.isEmpty()) {
        			this.txtResult.setText("Lo studente non è iscritto ad alcun corso.");
        		} 
        		//se sono stati inseriti sia matricola che corso, allora bisogna verificare se lo studente è iscritto a quel determinato corso
        		else if(corso!=null) {
        			if(corsiStudente.contains(corso)) {
        				this.txtResult.setText("Lo studente è già iscritto a questo corso.");
        			} else {
        				this.txtResult.setText("Lo studente non è iscritto a questo corso.");	
        			}
        		} 
        		//se la lista di corsi è piena e non è stato selezionato alcun corso, allora si stampano tutti i corsi seguiti dallo studente
        		else {
	        		this.txtResult.appendText("Lo studente " + matricola + " è iscritto a " + corsiStudente.size() + " corsi.\n");
	        		for(Corso c : corsiStudente) {
	        			this.txtResult.appendText(c + "\n");
	        		}
        		}
        	} 
        	else {
        		this.txtResult.setText("La matricola inserita non è presente nel database. Inserire una nuova matricola.");
        	}
    	}
    }

    @FXML
    void handleCercaIscrittiCorso(ActionEvent event) {
    	Corso corso = this.cmbCorsi.getValue();
    	
    	if(corso == null) {
    		this.txtResult.setText("Non è stato selezionato alcun corso. Riprovare.");
    	} else {
    		List<Studente> studentiIscrittiCorso = this.model.getStudentiIscrittiCorso(corso);
    		if(studentiIscrittiCorso.isEmpty()) {
	    		this.txtResult.setText("Il corso non ha iscritti.");
    		} else {
	    		this.txtResult.appendText("Al corso " + corso.getNome() + " sono iscritti " + studentiIscrittiCorso.size() + " studenti.\n");
	    		for(Studente s : studentiIscrittiCorso) {
	    			this.txtResult.appendText(s + "\n");
	    		}
    		}
    	}
    }

    @FXML
    void handleCercaStudente(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	
    	if(!matricola.isEmpty()) {
    		int matricolaNum = 0;
        	
        	try {
        		matricolaNum = Integer.parseInt(matricola);
        	} catch(NumberFormatException e) {
        		this.txtResult.setText("Il valore inserito non è di tipo intero.");
        		return;
        	}
        	
        	Studente studente = this.model.getStudenteFromMatricola(matricolaNum);
        	this.txtCognome.setText(studente.getCognome());
        	this.txtNome.setText(studente.getNome());
    	}
    }

    @FXML
    void handleIscrizione(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	Corso corso = this.cmbCorsi.getValue();
    	
    	if(!matricola.isEmpty()) {
    		int matricolaNum = 0;
        	
        	try {
        		matricolaNum = Integer.parseInt(matricola);
        	} catch(NumberFormatException e) {
        		this.txtResult.setText("Il valore inserito non è di tipo intero.");
        		return;
        	}
        	
        	Studente studente = this.model.getStudenteFromMatricola(matricolaNum);
        	
        	//se lo studente non è nullo, allora esiste
        	if(studente!=null && corso!=null) {
        		
        		List<Corso> corsiStudente = this.model.getCorsiStudente(matricolaNum);
        		
        		if(corsiStudente.contains(corso)) {
    				this.txtResult.setText("Lo studente è già iscritto a questo corso.");
    			} else {
    				//iscrivere lo studente al corso
    				boolean isIscritto = this.model.iscriviStudenteCorso(studente, corso);
    				if(isIscritto) {
        				this.txtResult.setText("Lo studente è stato iscritto a questo corso con successo.");	
    				} else {
        				this.txtResult.setText("Lo studente non è stato iscritto a questo corso. Errore.");	
    				}
    			}
        		
        	} 
        	else {
        		this.txtResult.setText("La matricola inserita non è presente nel database oppure non è stato selezionato alcun corso.");
        	}
    	}
    }

    @FXML
    void handleReset(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    }

    @FXML
    void initialize() {
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscriviti != null : "fx:id=\"btnIscriviti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbCorsi.getItems().add(null);
    	for(Corso c : this.model.getTuttiICorsi()) {
        	this.cmbCorsi.getItems().add(c);
        }
    }

}
