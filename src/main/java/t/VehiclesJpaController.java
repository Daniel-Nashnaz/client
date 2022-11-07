/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import SaveDB.Users;
import SaveDB.RealTimeInformation;
import SaveDB.Vehicles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.IllegalOrphanException;
import t.exceptions.NonexistentEntityException;

/**
 *
 * @author Daniel
 */
public class VehiclesJpaController implements Serializable {

    public VehiclesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehicles vehicles) {
        if (vehicles.getRealTimeInformationCollection() == null) {
            vehicles.setRealTimeInformationCollection(new ArrayList<RealTimeInformation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userID = vehicles.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getId());
                vehicles.setUserID(userID);
            }
            Collection<RealTimeInformation> attachedRealTimeInformationCollection = new ArrayList<RealTimeInformation>();
            for (RealTimeInformation realTimeInformationCollectionRealTimeInformationToAttach : vehicles.getRealTimeInformationCollection()) {
                realTimeInformationCollectionRealTimeInformationToAttach = em.getReference(realTimeInformationCollectionRealTimeInformationToAttach.getClass(), realTimeInformationCollectionRealTimeInformationToAttach.getId());
                attachedRealTimeInformationCollection.add(realTimeInformationCollectionRealTimeInformationToAttach);
            }
            vehicles.setRealTimeInformationCollection(attachedRealTimeInformationCollection);
            em.persist(vehicles);
            if (userID != null) {
                userID.getVehiclesCollection().add(vehicles);
                userID = em.merge(userID);
            }
            for (RealTimeInformation realTimeInformationCollectionRealTimeInformation : vehicles.getRealTimeInformationCollection()) {
                Vehicles oldTripIDOfRealTimeInformationCollectionRealTimeInformation = realTimeInformationCollectionRealTimeInformation.getTripID();
                realTimeInformationCollectionRealTimeInformation.setTripID(vehicles);
                realTimeInformationCollectionRealTimeInformation = em.merge(realTimeInformationCollectionRealTimeInformation);
                if (oldTripIDOfRealTimeInformationCollectionRealTimeInformation != null) {
                    oldTripIDOfRealTimeInformationCollectionRealTimeInformation.getRealTimeInformationCollection().remove(realTimeInformationCollectionRealTimeInformation);
                    oldTripIDOfRealTimeInformationCollectionRealTimeInformation = em.merge(oldTripIDOfRealTimeInformationCollectionRealTimeInformation);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehicles vehicles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicles persistentVehicles = em.find(Vehicles.class, vehicles.getTripID());
            Users userIDOld = persistentVehicles.getUserID();
            Users userIDNew = vehicles.getUserID();
            Collection<RealTimeInformation> realTimeInformationCollectionOld = persistentVehicles.getRealTimeInformationCollection();
            Collection<RealTimeInformation> realTimeInformationCollectionNew = vehicles.getRealTimeInformationCollection();
            List<String> illegalOrphanMessages = null;
            for (RealTimeInformation realTimeInformationCollectionOldRealTimeInformation : realTimeInformationCollectionOld) {
                if (!realTimeInformationCollectionNew.contains(realTimeInformationCollectionOldRealTimeInformation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RealTimeInformation " + realTimeInformationCollectionOldRealTimeInformation + " since its tripID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                vehicles.setUserID(userIDNew);
            }
            Collection<RealTimeInformation> attachedRealTimeInformationCollectionNew = new ArrayList<RealTimeInformation>();
            for (RealTimeInformation realTimeInformationCollectionNewRealTimeInformationToAttach : realTimeInformationCollectionNew) {
                realTimeInformationCollectionNewRealTimeInformationToAttach = em.getReference(realTimeInformationCollectionNewRealTimeInformationToAttach.getClass(), realTimeInformationCollectionNewRealTimeInformationToAttach.getId());
                attachedRealTimeInformationCollectionNew.add(realTimeInformationCollectionNewRealTimeInformationToAttach);
            }
            realTimeInformationCollectionNew = attachedRealTimeInformationCollectionNew;
            vehicles.setRealTimeInformationCollection(realTimeInformationCollectionNew);
            vehicles = em.merge(vehicles);
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getVehiclesCollection().remove(vehicles);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getVehiclesCollection().add(vehicles);
                userIDNew = em.merge(userIDNew);
            }
            for (RealTimeInformation realTimeInformationCollectionNewRealTimeInformation : realTimeInformationCollectionNew) {
                if (!realTimeInformationCollectionOld.contains(realTimeInformationCollectionNewRealTimeInformation)) {
                    Vehicles oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation = realTimeInformationCollectionNewRealTimeInformation.getTripID();
                    realTimeInformationCollectionNewRealTimeInformation.setTripID(vehicles);
                    realTimeInformationCollectionNewRealTimeInformation = em.merge(realTimeInformationCollectionNewRealTimeInformation);
                    if (oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation != null && !oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation.equals(vehicles)) {
                        oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation.getRealTimeInformationCollection().remove(realTimeInformationCollectionNewRealTimeInformation);
                        oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation = em.merge(oldTripIDOfRealTimeInformationCollectionNewRealTimeInformation);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicles.getTripID();
                if (findVehicles(id) == null) {
                    throw new NonexistentEntityException("The vehicles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicles vehicles;
            try {
                vehicles = em.getReference(Vehicles.class, id);
                vehicles.getTripID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehicles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RealTimeInformation> realTimeInformationCollectionOrphanCheck = vehicles.getRealTimeInformationCollection();
            for (RealTimeInformation realTimeInformationCollectionOrphanCheckRealTimeInformation : realTimeInformationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehicles (" + vehicles + ") cannot be destroyed since the RealTimeInformation " + realTimeInformationCollectionOrphanCheckRealTimeInformation + " in its realTimeInformationCollection field has a non-nullable tripID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userID = vehicles.getUserID();
            if (userID != null) {
                userID.getVehiclesCollection().remove(vehicles);
                userID = em.merge(userID);
            }
            em.remove(vehicles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehicles> findVehiclesEntities() {
        return findVehiclesEntities(true, -1, -1);
    }

    public List<Vehicles> findVehiclesEntities(int maxResults, int firstResult) {
        return findVehiclesEntities(false, maxResults, firstResult);
    }

    private List<Vehicles> findVehiclesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehicles.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vehicles findVehicles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehicles.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiclesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehicles> rt = cq.from(Vehicles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
