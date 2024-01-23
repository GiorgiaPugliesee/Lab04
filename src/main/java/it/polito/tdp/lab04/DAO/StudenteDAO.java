package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudenteFromMatricola(int matricola) {

		final String sql = "SELECT * FROM studente WHERE matricola = ? ";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
	            // Move the cursor to the first row and retrieve data
	            Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));

	            conn.close();
	            return studente;
	        }
			
			conn.close();
			
			return null;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	

}
