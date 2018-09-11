/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "Profesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p")
    , @NamedQuery(name = "Profesor.findByPkIdProfesor", query = "SELECT p FROM Profesor p WHERE p.pkIdProfesor = :pkIdProfesor")
    , @NamedQuery(name = "Profesor.findByCostoXHora", query = "SELECT p FROM Profesor p WHERE p.costoXHora = :costoXHora")
    , @NamedQuery(name = "Profesor.findByNivelesEducativos", query = "SELECT p FROM Profesor p WHERE p.nivelesEducativos = :nivelesEducativos")
    , @NamedQuery(name = "Profesor.findByHabilidades", query = "SELECT p FROM Profesor p WHERE p.habilidades = :habilidades")})
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_profesor")
    private Integer pkIdProfesor;
    @Lob
    @Column(name = "identificacion_identidad")
    private byte[] identificacionIdentidad;
    @Size(max = 120)
    @Column(name = "costo_x_hora")
    private String costoXHora;
    @Size(max = 120)
    @Column(name = "niveles_educativos")
    private String nivelesEducativos;
    @Size(max = 320)
    @Column(name = "habilidades")
    private String habilidades;
    @JoinColumn(name = "fk_id_usuario", referencedColumnName = "pk_id_usuario")
    @ManyToOne(optional = false)
    private Usuario fkIdUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProfesor")
    private List<Curriculum> curriculumList;

    public Profesor() {
    }

    public Profesor(Integer pkIdProfesor) {
        this.pkIdProfesor = pkIdProfesor;
    }

    public Integer getPkIdProfesor() {
        return pkIdProfesor;
    }

    public void setPkIdProfesor(Integer pkIdProfesor) {
        this.pkIdProfesor = pkIdProfesor;
    }

    public byte[] getIdentificacionIdentidad() {
        return identificacionIdentidad;
    }

    public void setIdentificacionIdentidad(byte[] identificacionIdentidad) {
        this.identificacionIdentidad = identificacionIdentidad;
    }

    public String getCostoXHora() {
        return costoXHora;
    }

    public void setCostoXHora(String costoXHora) {
        this.costoXHora = costoXHora;
    }

    public String getNivelesEducativos() {
        return nivelesEducativos;
    }

    public void setNivelesEducativos(String nivelesEducativos) {
        this.nivelesEducativos = nivelesEducativos;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public Usuario getFkIdUsuario() {
        return fkIdUsuario;
    }

    public void setFkIdUsuario(Usuario fkIdUsuario) {
        this.fkIdUsuario = fkIdUsuario;
    }

    @XmlTransient
    public List<Curriculum> getCurriculumList() {
        return curriculumList;
    }

    public void setCurriculumList(List<Curriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProfesor != null ? pkIdProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.pkIdProfesor == null && other.pkIdProfesor != null) || (this.pkIdProfesor != null && !this.pkIdProfesor.equals(other.pkIdProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Profesor[ pkIdProfesor=" + pkIdProfesor + " ]";
    }
    
}
