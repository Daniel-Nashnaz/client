/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SaveDB;

import java.io.Serializable;
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

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "UserPasswords", catalog = "DrivingControl", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "UserPasswords.findAll", query = "SELECT u FROM UserPasswords u"),
    @NamedQuery(name = "UserPasswords.findById", query = "SELECT u FROM UserPasswords u WHERE u.id = :id"),
    @NamedQuery(name = "UserPasswords.findByPassword", query = "SELECT u FROM UserPasswords u WHERE u.password = :password"),
    @NamedQuery(name = "UserPasswords.findByIsActive", query = "SELECT u FROM UserPasswords u WHERE u.isActive = :isActive")})
public class UserPasswords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userID;

    public UserPasswords() {
    }

    public UserPasswords(Integer id) {
        this.id = id;
    }

    public UserPasswords(Integer id, String password, boolean isActive) {
        this.id = id;
        this.password = password;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
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
        if (!(object instanceof UserPasswords)) {
            return false;
        }
        UserPasswords other = (UserPasswords) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaveDB.UserPasswords[ id=" + id + " ]";
    }
    
}
