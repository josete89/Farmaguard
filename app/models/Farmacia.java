package models;
 
import javax.persistence.*;

import play.db.jpa.Model;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Farmacia extends Model {

	public String titular;
	public String direccion;
	public String localidad;
	public String telefono;
	public String fax;
	public String observaciones;
    
    public String latitude;
    public String longitude;
	
	public String scrapeId;

    @ManyToMany(mappedBy="farmacias")
    public Set<Horario> horarios = new HashSet<Horario>();
	
	private Farmacia(Builder builder) {

        this.titular = builder.titular;
        this.direccion = builder.direccion;
        this.localidad = builder.localidad;
        this.telefono = builder.telefono;
        this.fax = builder.fax;
        this.observaciones = builder.observaciones;
        this.scrapeId = builder.scrapeId;
    }

    private Farmacia() {
	}

	public static class Builder {

    	private String titular;
    	private String direccion;
    	private String localidad;
    	private String telefono;
    	private String fax;
    	private String observaciones;
    	private String scrapeId;

        public Builder(String titular) {
            this.titular = titular;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }
        
        public Builder localidad(String localidad) {
            this.localidad = localidad;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder fax(String fax) {
            this.fax = fax;
            return this;
        }
        
        public Builder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }
        
        public Builder scrapeId(String scrapeId) {
            this.scrapeId = scrapeId;
            return this;
        }
        
        public Farmacia build(){
        	Farmacia farmacia = new Farmacia(this);
        	return farmacia;
        }
    }
	
	public String toString () {
		return "#titular: " + titular + " #direccion: " + direccion + " #localidad: " + localidad + " #telefono: " + telefono + " #fax: " + fax + " #observaciones: " + observaciones + "\n";
	}
	

}
