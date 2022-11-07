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
@Table(name = "RealTimeInformation", catalog = "DrivingControl", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "RealTimeInformation.findAll", query = "SELECT r FROM RealTimeInformation r"),
    @NamedQuery(name = "RealTimeInformation.findById", query = "SELECT r FROM RealTimeInformation r WHERE r.id = :id"),
    @NamedQuery(name = "RealTimeInformation.findByTimeFromStart", query = "SELECT r FROM RealTimeInformation r WHERE r.TimeFromStart = :TimeFromStart"),
    @NamedQuery(name = "RealTimeInformation.findByLatitude", query = "SELECT r FROM RealTimeInformation r WHERE r.latitude = :latitude"),
    @NamedQuery(name = "RealTimeInformation.findByLongitude", query = "SELECT r FROM RealTimeInformation r WHERE r.longitude = :longitude"),
    @NamedQuery(name = "RealTimeInformation.findByForwardWarningDirections", query = "SELECT r FROM RealTimeInformation r WHERE r.ForwardWarningDirections = :ForwardWarningDirections"),
    @NamedQuery(name = "RealTimeInformation.findByForwardWarningDistance", query = "SELECT r FROM RealTimeInformation r WHERE r.ForwardWarningDistance = :ForwardWarningDistance"),
    @NamedQuery(name = "RealTimeInformation.findByLaneDepartureWarning", query = "SELECT r FROM RealTimeInformation r WHERE r.LaneDepartureWarning = :LaneDepartureWarning"),
    @NamedQuery(name = "RealTimeInformation.findByPedestrianAndCyclistCollisionWarning", query = "SELECT r FROM RealTimeInformation r WHERE r.PedestrianAndCyclistCollisionWarning = :PedestrianAndCyclistCollisionWarning"),
    @NamedQuery(name = "RealTimeInformation.findBySuddenBraking", query = "SELECT r FROM RealTimeInformation r WHERE r.SuddenBraking = :SuddenBraking"),
    @NamedQuery(name = "RealTimeInformation.findBySpeedAllowed", query = "SELECT r FROM RealTimeInformation r WHERE r.speedAllowed = :speedAllowed"),
    @NamedQuery(name = "RealTimeInformation.findByCurrentSpeed", query = "SELECT r FROM RealTimeInformation r WHERE r.currentSpeed = :currentSpeed"),
    @NamedQuery(name = "RealTimeInformation.findByDistanceTraveledMile", query = "SELECT r FROM RealTimeInformation r WHERE r.distanceTraveledMile = :distanceTraveledMile")})
public class RealTimeInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TimeFromStart")
    private String TimeFromStart;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private double latitude;
    @Basic(optional = false)
    @Column(name = "Longitude")
    private double longitude;
    @Basic(optional = false)
    @Column(name = "ForwardWarningDirections")
    private String ForwardWarningDirections;
    @Basic(optional = false)
    @Column(name = "ForwardWarningDistance")
    private String ForwardWarningDistance;
    @Basic(optional = false)
    @Column(name = "LaneDepartureWarning")
    private String LaneDepartureWarning;
    @Basic(optional = false)
    @Column(name = "PedestrianAndCyclistCollisionWarning")
    private String PedestrianAndCyclistCollisionWarning;
    @Basic(optional = false)
    @Column(name = "SuddenBraking")
    private boolean SuddenBraking;
    @Basic(optional = false)
    @Column(name = "SpeedAllowed")
    private int speedAllowed;
    @Basic(optional = false)
    @Column(name = "CurrentSpeed")
    private int currentSpeed;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DistanceTraveledMile")
    private Double distanceTraveledMile;
    @JoinColumn(name = "TripID", referencedColumnName = "TripID")
    @ManyToOne(optional = false)
    private Vehicles tripID;

    public RealTimeInformation() {
    }

    public RealTimeInformation(Integer id) {
        this.id = id;
    }

    public RealTimeInformation(Integer id, String TimeFromStart, double latitude, double longitude, String ForwardWarningDirections, String ForwardWarningDistance, String LaneDepartureWarning, String PedestrianAndCyclistCollisionWarning, boolean SuddenBraking, int speedAllowed, int currentSpeed) {
        this.id = id;
        this.TimeFromStart = TimeFromStart;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ForwardWarningDirections = ForwardWarningDirections;
        this.ForwardWarningDistance = ForwardWarningDistance;
        this.LaneDepartureWarning = LaneDepartureWarning;
        this.PedestrianAndCyclistCollisionWarning = PedestrianAndCyclistCollisionWarning;
        this.SuddenBraking = SuddenBraking;
        this.speedAllowed = speedAllowed;
        this.currentSpeed = currentSpeed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeFromStart() {
        return TimeFromStart;
    }

    public void setTimeFromStart(String TimeFromStart) {
        this.TimeFromStart = TimeFromStart;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getForwardWarningDirections() {
        return ForwardWarningDirections;
    }

    public void setForwardWarningDirections(String ForwardWarningDirections) {
        this.ForwardWarningDirections = ForwardWarningDirections;
    }

    public String getForwardWarningDistance() {
        return ForwardWarningDistance;
    }

    public void setForwardWarningDistance(String ForwardWarningDistance) {
        this.ForwardWarningDistance = ForwardWarningDistance;
    }

    public String getLaneDepartureWarning() {
        return LaneDepartureWarning;
    }

    public void setLaneDepartureWarning(String LaneDepartureWarning) {
        this.LaneDepartureWarning = LaneDepartureWarning;
    }

    public String getPedestrianAndCyclistCollisionWarning() {
        return PedestrianAndCyclistCollisionWarning;
    }

    public void setPedestrianAndCyclistCollisionWarning(String PedestrianAndCyclistCollisionWarning) {
        this.PedestrianAndCyclistCollisionWarning = PedestrianAndCyclistCollisionWarning;
    }

    public boolean getSuddenBraking() {
        return SuddenBraking;
    }

    public void setSuddenBraking(boolean SuddenBraking) {
        this.SuddenBraking = SuddenBraking;
    }

    public int getSpeedAllowed() {
        return speedAllowed;
    }

    public void setSpeedAllowed(int speedAllowed) {
        this.speedAllowed = speedAllowed;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Double getDistanceTraveledMile() {
        return distanceTraveledMile;
    }

    public void setDistanceTraveledMile(Double distanceTraveledMile) {
        this.distanceTraveledMile = distanceTraveledMile;
    }

    public Vehicles getTripID() {
        return tripID;
    }

    public void setTripID(Vehicles tripID) {
        this.tripID = tripID;
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
        if (!(object instanceof RealTimeInformation)) {
            return false;
        }
        RealTimeInformation other = (RealTimeInformation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaveDB.RealTimeInformation[ id=" + id + " ]";
    }
    
}
