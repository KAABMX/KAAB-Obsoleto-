/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.mapeobd;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Curriculum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curriculum.findAll", query = "SELECT c FROM Curriculum c")
    , @NamedQuery(name = "Curriculum.findByPkIdCv", query = "SELECT c FROM Curriculum c WHERE c.pkIdCv = :pkIdCv")
    , @NamedQuery(name = "Curriculum.findByLugarDeNacimiento", query = "SELECT c FROM Curriculum c WHERE c.lugarDeNacimiento = :lugarDeNacimiento")})
public class Curriculum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_cv")
    private Integer pkIdCv;
    @Size(max = 90)
    @Column(name = "lugar_de_nacimiento")
    private String lugarDeNacimiento;
    @OneToMany(mappedBy = "fkIdCv")
    private List<Complementario> complementarioList;
    @OneToMany(mappedBy = "fkIdCv")
    private List<Estudio> estudioList;
    @JoinColumn(name = "fk_id_profesor", referencedColumnName = "pk_id_profesor")
    @ManyToOne(optional = false)
    private Profesor fkIdProfesor;
    @OneToMany(mappedBy = "fkIdCv")
    private List<Experiencia> experienciaList;

    public Curriculum() {
    }

    public Curriculum(Integer pkIdCv) {
        this.pkIdCv = pkIdCv;
    }

    public Integer getPkIdCv() {
        return pkIdCv;
    }

    public void setPkIdCv(Integer pkIdCv) {
        this.pkIdCv = pkIdCv;
    }

    public String getLugarDeNacimiento() {
        return lugarDeNacimiento;
    }

    public void setLugarDeNacimiento(String lugarDeNacimiento) {
        this.lugarDeNacimiento = lugarDeNacimiento;
    }

    @XmlTransient
    public List<Complementario> getComplementarioList() {
        return complementarioList;
    }

    public void setComplementarioList(List<Complementario> complementarioList) {
        this.complementarioList = complementarioList;
    }

    @XmlTransient
    public List<Estudio> getEstudioList() {
        return estudioList;
    }

    public void setEstudioList(List<Estudio> estudioList) {
        this.estudioList = estudioList;
    }

    public Profesor getFkIdProfesor() {
        return fkIdProfesor;
    }

    public void setFkIdProfesor(Profesor fkIdProfesor) {
        this.fkIdProfesor = fkIdProfesor;
    }

    @XmlTransient
    public List<Experiencia> getExperienciaList() {
        return experienciaList;
    }

    public void setExperienciaList(List<Experiencia> experienciaList) {
        this.experienciaList = experienciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCv != null ? pkIdCv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curriculum)) {
            return false;
        }
        Curriculum other = (Curriculum) object;
        if ((this.pkIdCv == null && other.pkIdCv != null) || (this.pkIdCv != null && !this.pkIdCv.equals(other.pkIdCv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Curriculum[ pkIdCv=" + pkIdCv + " ]";
    }
    
}
