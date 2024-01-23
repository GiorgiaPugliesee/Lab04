package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

//				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}
	
	/*
	 * Ottengo tutti i corsi a cui è iscritto uno studente
	 */
	public List<Corso> getCorsiStudente(int matricola) {
		final String sql = " SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM iscrizione i, corso c "
				+ "WHERE i.matricola = ? AND i.codins = c.codins ";

		List<Corso> corsiStudente = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corsiStudente.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}

			conn.close();
			
			return corsiStudente;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}	
	}


	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql = " SELECT s.matricola, s.nome, s.cognome, s.CDS "
				+ " FROM iscrizione i, studente s "
				+ " WHERE i.codins = ? AND i.matricola = s.matricola ";

		List<Studente> studentiIscrittiCorso = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String CDS = rs.getString("CDS");

//				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				studentiIscrittiCorso.add(new Studente(matricola, cognome, nome, CDS));
			}

			conn.close();
			
			return studentiIscrittiCorso;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		String sql = "INSERT INTO iscrizione(matricola, codins) "
				+ " VALUES(?, ?) ";
		
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			rs.close();
			st.close();
			conn.close();
			return true;
			
		} catch (SQLException e) {
			System.err.println("Errore connessione al database.");
			e.printStackTrace();
			return false;
		}
	}

}
