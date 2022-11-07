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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Users", catalog = "DrivingControl", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByIsDeleted", query = "SELECT u FROM Users u WHERE u.isDeleted = :isDeleted")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "UserName")
    private String userName;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "IsDeleted")
    private boolean isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<UserPasswords> userPasswordsCollection;
    @JoinColumn(name = "ManagerByID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Managers managerByID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Vehicles> vehiclesCollection;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String userName, String email, boolean isDeleted) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Collection<UserPasswords> getUserPasswordsCollection() {
        return userPasswordsCollection;
    }

    public void setUserPasswordsCollection(Collection<UserPasswords> userPasswordsCollection) {
        this.userPasswordsCollection = userPasswordsCollection;
    }

    public Managers getManagerByID() {
        return managerByID;
    }

    public void setManagerByID(Managers managerByID) {
        this.managerByID = managerByID;
    }

    public Collection<Vehicles> getVehiclesCollection() {
        return vehiclesCollection;
    }

    public void setVehiclesCollection(Collection<Vehicles> vehiclesCollection) {
        this.vehiclesCollection = vehiclesCollection;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaveDB.Users[ id=" + id + " ]";
    }
    
}
