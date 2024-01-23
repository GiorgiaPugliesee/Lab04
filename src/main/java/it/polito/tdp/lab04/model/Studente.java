package it.polito.tdp.lab04.model;

import java.util.Objects;

public class Studente {
	
	private int matricola;
	private String cognome;
	private String nome;
	private String CDS;
	
	public Studente(int matricola, String cognome, String nome, String cDS) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		CDS = cDS;
	}
	
	public int getMatricola() {
		return matricola;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCDS() {
		return CDS;
	}
	
	@Override
	public String toString() {
		return matricola + ", " + nome + ", " + cognome + ", " + CDS;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(CDS, cognome, matricola, nome);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		return Objects.equals(CDS, other.CDS) && Objects.equals(cognome, other.cognome) && matricola == other.matricola
				&& Objects.equals(nome, other.nome);
	}
	
	

}
