package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import play.db.jpa.Model;

@Entity
public class Horario extends Model {

    public Date fechaHoraInicio;
    public Date fechaHoraFin;

    @ManyToMany
    public Set<Farmacia> farmacias = new HashSet<Farmacia>();

}
