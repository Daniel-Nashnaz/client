/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SaveDB;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Managers", catalog = "DrivingControl", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Managers.findAll", query = "SELECT m FROM Managers m"),
    @NamedQuery(name = "Managers.findById", query = "SELECT m FROM Managers m WHERE m.id = :id"),
    @NamedQuery(name = "Managers.findByManagerName", query = "SELECT m FROM Managers m WHERE m.managerName = :managerName"),
    @NamedQuery(name = "Managers.findByEmail", query = "SELECT m FROM Managers m WHERE m.email = :email"),
    @NamedQuery(name = "Managers.findByIsDelete", query = "SELECT m FROM Managers m WHERE m.isDelete = :isDelete")})
public class Managers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ManagerName")
    private String managerName;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "IsDelete")
    private boolean isDelete;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "managerID")
    private Collection<ManagerPasswords> managerPasswordsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "managerByID")
    private Collection<Users> usersCollection;

    public Managers() {
    }

    public Managers(Integer id) {
        this.id = id;
    }

    public Managers(Integer id, String managerName, String email, boolean isDelete) {
        this.id = id;
        this.managerName = managerName;
        this.email = email;
        this.isDelete = isDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Collection<ManagerPasswords> getManagerPasswordsCollection() {
        return managerPasswordsCollection;
    }

    public void setManagerPasswordsCollection(Collection<ManagerPasswords> managerPasswordsCollection) {
        this.managerPasswordsCollection = managerPasswordsCollection;
    }

    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Managers)) {
            return false;
        }
        Managers other = (Managers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaveDB.Managers[ id=" + id + " ]";
    }
    
}
