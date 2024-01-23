package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi() {
		return this.corsoDao.getTuttiICorsi();
	}
	
	public Studente getStudenteFromMatricola(int matricola) {
		return this.studenteDao.getStudenteFromMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiCorso(Corso corso) {
		return this.corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiStudente(int matricola) {
		if(getStudenteFromMatricola(matricola)!=null) {
			return this.corsoDao.getCorsiStudente(matricola);
		}
		return null;
	}
	
	public boolean iscriviStudenteCorso(Studente studente, Corso corso) {
		return this.corsoDao.inscriviStudenteACorso(studente, corso);
	}

}
