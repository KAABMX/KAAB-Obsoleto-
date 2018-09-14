/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.mapeobd;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Estudios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudio.findAll", query = "SELECT e FROM Estudio e")
    , @NamedQuery(name = "Estudio.findByPkIdEstudios", query = "SELECT e FROM Estudio e WHERE e.pkIdEstudios = :pkIdEstudios")
    , @NamedQuery(name = "Estudio.findByEstudio", query = "SELECT e FROM Estudio e WHERE e.estudio = :estudio")
    , @NamedQuery(name = "Estudio.findByFechaInicio", query = "SELECT e FROM Estudio e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Estudio.findByFechaFin", query = "SELECT e FROM Estudio e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "Estudio.findByUniversidad", query = "SELECT e FROM Estudio e WHERE e.universidad = :universidad")})
public class Estudio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estudios")
    private Integer pkIdEstudios;
    @Size(max = 90)
    @Column(name = "estudio")
    private String estudio;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 120)
    @Column(name = "universidad")
    private String universidad;
    @JoinColumn(name = "fk_id_cv", referencedColumnName = "pk_id_cv")
    @ManyToOne
    private Curriculum fkIdCv;

    public Estudio() {
    }

    public Estudio(Integer pkIdEstudios) {
        this.pkIdEstudios = pkIdEstudios;
    }

    public Integer getPkIdEstudios() {
        return pkIdEstudios;
    }

    public void setPkIdEstudios(Integer pkIdEstudios) {
        this.pkIdEstudios = pkIdEstudios;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public Curriculum getFkIdCv() {
        return fkIdCv;
    }

    public void setFkIdCv(Curriculum fkIdCv) {
        this.fkIdCv = fkIdCv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstudios != null ? pkIdEstudios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudio)) {
            return false;
        }
        Estudio other = (Estudio) object;
        if ((this.pkIdEstudios == null && other.pkIdEstudios != null) || (this.pkIdEstudios != null && !this.pkIdEstudios.equals(other.pkIdEstudios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Estudio[ pkIdEstudios=" + pkIdEstudios + " ]";
    }
    
}
