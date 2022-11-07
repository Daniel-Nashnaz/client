/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SaveDB;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Vehicles", catalog = "DrivingControl", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Vehicles.findAll", query = "SELECT v FROM Vehicles v"),
    @NamedQuery(name = "Vehicles.findByTripID", query = "SELECT v FROM Vehicles v WHERE v.tripID = :tripID"),
    @NamedQuery(name = "Vehicles.findByTravelStart", query = "SELECT v FROM Vehicles v WHERE v.travelStart = :travelStart"),
    @NamedQuery(name = "Vehicles.findByVehicleNumber", query = "SELECT v FROM Vehicles v WHERE v.vehicleNumber = :vehicleNumber"),
    @NamedQuery(name = "Vehicles.findByVehicleName", query = "SELECT v FROM Vehicles v WHERE v.vehicleName = :vehicleName")})
public class Vehicles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TripID")
    private Integer tripID;
    @Basic(optional = false)
    @Column(name = "TravelStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date travelStart;
    @Basic(optional = false)
    @Column(name = "VehicleNumber")
    private String vehicleNumber;
    @Basic(optional = false)
    @Column(name = "VehicleName")
    private String vehicleName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tripID")
    private Collection<RealTimeInformation> realTimeInformationCollection;
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userID;

    public Vehicles() {
    }

    public Vehicles(Integer tripID) {
        this.tripID = tripID;
    }

    public Vehicles(Integer tripID, Date travelStart, String vehicleNumber, String vehicleName) {
        this.tripID = tripID;
        this.travelStart = travelStart;
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
    }

    public Integer getTripID() {
        return tripID;
    }

    public void setTripID(Integer tripID) {
        this.tripID = tripID;
    }

    public Date getTravelStart() {
        return travelStart;
    }

    public void setTravelStart(Date travelStart) {
        this.travelStart = travelStart;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Collection<RealTimeInformation> getRealTimeInformationCollection() {
        return realTimeInformationCollection;
    }

    public void setRealTimeInformationCollection(Collection<RealTimeInformation> realTimeInformationCollection) {
        this.realTimeInformationCollection = realTimeInformationCollection;
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
        hash += (tripID != null ? tripID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicles)) {
            return false;
        }
        Vehicles other = (Vehicles) object;
        if ((this.tripID == null && other.tripID != null) || (this.tripID != null && !this.tripID.equals(other.tripID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaveDB.Vehicles[ tripID=" + tripID + " ]";
    }
    
}
