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
import SaveDB.Managers;
import SaveDB.UserPasswords;
import SaveDB.Users;
import java.util.ArrayList;
import java.util.Collection;
import SaveDB.Vehicles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.IllegalOrphanException;
import t.exceptions.NonexistentEntityException;

/**
 *
 * @author Daniel
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        if (users.getUserPasswordsCollection() == null) {
            users.setUserPasswordsCollection(new ArrayList<UserPasswords>());
        }
        if (users.getVehiclesCollection() == null) {
            users.setVehiclesCollection(new ArrayList<Vehicles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Managers managerByID = users.getManagerByID();
            if (managerByID != null) {
                managerByID = em.getReference(managerByID.getClass(), managerByID.getId());
                users.setManagerByID(managerByID);
            }
            Collection<UserPasswords> attachedUserPasswordsCollection = new ArrayList<UserPasswords>();
            for (UserPasswords userPasswordsCollectionUserPasswordsToAttach : users.getUserPasswordsCollection()) {
                userPasswordsCollectionUserPasswordsToAttach = em.getReference(userPasswordsCollectionUserPasswordsToAttach.getClass(), userPasswordsCollectionUserPasswordsToAttach.getId());
                attachedUserPasswordsCollection.add(userPasswordsCollectionUserPasswordsToAttach);
            }
            users.setUserPasswordsCollection(attachedUserPasswordsCollection);
            Collection<Vehicles> attachedVehiclesCollection = new ArrayList<Vehicles>();
            for (Vehicles vehiclesCollectionVehiclesToAttach : users.getVehiclesCollection()) {
                vehiclesCollectionVehiclesToAttach = em.getReference(vehiclesCollectionVehiclesToAttach.getClass(), vehiclesCollectionVehiclesToAttach.getTripID());
                attachedVehiclesCollection.add(vehiclesCollectionVehiclesToAttach);
            }
            users.setVehiclesCollection(attachedVehiclesCollection);
            em.persist(users);
            if (managerByID != null) {
                managerByID.getUsersCollection().add(users);
                managerByID = em.merge(managerByID);
            }
            for (UserPasswords userPasswordsCollectionUserPasswords : users.getUserPasswordsCollection()) {
                Users oldUserIDOfUserPasswordsCollectionUserPasswords = userPasswordsCollectionUserPasswords.getUserID();
                userPasswordsCollectionUserPasswords.setUserID(users);
                userPasswordsCollectionUserPasswords = em.merge(userPasswordsCollectionUserPasswords);
                if (oldUserIDOfUserPasswordsCollectionUserPasswords != null) {
                    oldUserIDOfUserPasswordsCollectionUserPasswords.getUserPasswordsCollection().remove(userPasswordsCollectionUserPasswords);
                    oldUserIDOfUserPasswordsCollectionUserPasswords = em.merge(oldUserIDOfUserPasswordsCollectionUserPasswords);
                }
            }
            for (Vehicles vehiclesCollectionVehicles : users.getVehiclesCollection()) {
                Users oldUserIDOfVehiclesCollectionVehicles = vehiclesCollectionVehicles.getUserID();
                vehiclesCollectionVehicles.setUserID(users);
                vehiclesCollectionVehicles = em.merge(vehiclesCollectionVehicles);
                if (oldUserIDOfVehiclesCollectionVehicles != null) {
                    oldUserIDOfVehiclesCollectionVehicles.getVehiclesCollection().remove(vehiclesCollectionVehicles);
                    oldUserIDOfVehiclesCollectionVehicles = em.merge(oldUserIDOfVehiclesCollectionVehicles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            Managers managerByIDOld = persistentUsers.getManagerByID();
            Managers managerByIDNew = users.getManagerByID();
            Collection<UserPasswords> userPasswordsCollectionOld = persistentUsers.getUserPasswordsCollection();
            Collection<UserPasswords> userPasswordsCollectionNew = users.getUserPasswordsCollection();
            Collection<Vehicles> vehiclesCollectionOld = persistentUsers.getVehiclesCollection();
            Collection<Vehicles> vehiclesCollectionNew = users.getVehiclesCollection();
            List<String> illegalOrphanMessages = null;
            for (UserPasswords userPasswordsCollectionOldUserPasswords : userPasswordsCollectionOld) {
                if (!userPasswordsCollectionNew.contains(userPasswordsCollectionOldUserPasswords)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserPasswords " + userPasswordsCollectionOldUserPasswords + " since its userID field is not nullable.");
                }
            }
            for (Vehicles vehiclesCollectionOldVehicles : vehiclesCollectionOld) {
                if (!vehiclesCollectionNew.contains(vehiclesCollectionOldVehicles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vehicles " + vehiclesCollectionOldVehicles + " since its userID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (managerByIDNew != null) {
                managerByIDNew = em.getReference(managerByIDNew.getClass(), managerByIDNew.getId());
                users.setManagerByID(managerByIDNew);
            }
            Collection<UserPasswords> attachedUserPasswordsCollectionNew = new ArrayList<UserPasswords>();
            for (UserPasswords userPasswordsCollectionNewUserPasswordsToAttach : userPasswordsCollectionNew) {
                userPasswordsCollectionNewUserPasswordsToAttach = em.getReference(userPasswordsCollectionNewUserPasswordsToAttach.getClass(), userPasswordsCollectionNewUserPasswordsToAttach.getId());
                attachedUserPasswordsCollectionNew.add(userPasswordsCollectionNewUserPasswordsToAttach);
            }
            userPasswordsCollectionNew = attachedUserPasswordsCollectionNew;
            users.setUserPasswordsCollection(userPasswordsCollectionNew);
            Collection<Vehicles> attachedVehiclesCollectionNew = new ArrayList<Vehicles>();
            for (Vehicles vehiclesCollectionNewVehiclesToAttach : vehiclesCollectionNew) {
                vehiclesCollectionNewVehiclesToAttach = em.getReference(vehiclesCollectionNewVehiclesToAttach.getClass(), vehiclesCollectionNewVehiclesToAttach.getTripID());
                attachedVehiclesCollectionNew.add(vehiclesCollectionNewVehiclesToAttach);
            }
            vehiclesCollectionNew = attachedVehiclesCollectionNew;
            users.setVehiclesCollection(vehiclesCollectionNew);
            users = em.merge(users);
            if (managerByIDOld != null && !managerByIDOld.equals(managerByIDNew)) {
                managerByIDOld.getUsersCollection().remove(users);
                managerByIDOld = em.merge(managerByIDOld);
            }
            if (managerByIDNew != null && !managerByIDNew.equals(managerByIDOld)) {
                managerByIDNew.getUsersCollection().add(users);
                managerByIDNew = em.merge(managerByIDNew);
            }
            for (UserPasswords userPasswordsCollectionNewUserPasswords : userPasswordsCollectionNew) {
                if (!userPasswordsCollectionOld.contains(userPasswordsCollectionNewUserPasswords)) {
                    Users oldUserIDOfUserPasswordsCollectionNewUserPasswords = userPasswordsCollectionNewUserPasswords.getUserID();
                    userPasswordsCollectionNewUserPasswords.setUserID(users);
                    userPasswordsCollectionNewUserPasswords = em.merge(userPasswordsCollectionNewUserPasswords);
                    if (oldUserIDOfUserPasswordsCollectionNewUserPasswords != null && !oldUserIDOfUserPasswordsCollectionNewUserPasswords.equals(users)) {
                        oldUserIDOfUserPasswordsCollectionNewUserPasswords.getUserPasswordsCollection().remove(userPasswordsCollectionNewUserPasswords);
                        oldUserIDOfUserPasswordsCollectionNewUserPasswords = em.merge(oldUserIDOfUserPasswordsCollectionNewUserPasswords);
                    }
                }
            }
            for (Vehicles vehiclesCollectionNewVehicles : vehiclesCollectionNew) {
                if (!vehiclesCollectionOld.contains(vehiclesCollectionNewVehicles)) {
                    Users oldUserIDOfVehiclesCollectionNewVehicles = vehiclesCollectionNewVehicles.getUserID();
                    vehiclesCollectionNewVehicles.setUserID(users);
                    vehiclesCollectionNewVehicles = em.merge(vehiclesCollectionNewVehicles);
                    if (oldUserIDOfVehiclesCollectionNewVehicles != null && !oldUserIDOfVehiclesCollectionNewVehicles.equals(users)) {
                        oldUserIDOfVehiclesCollectionNewVehicles.getVehiclesCollection().remove(vehiclesCollectionNewVehicles);
                        oldUserIDOfVehiclesCollectionNewVehicles = em.merge(oldUserIDOfVehiclesCollectionNewVehicles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UserPasswords> userPasswordsCollectionOrphanCheck = users.getUserPasswordsCollection();
            for (UserPasswords userPasswordsCollectionOrphanCheckUserPasswords : userPasswordsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the UserPasswords " + userPasswordsCollectionOrphanCheckUserPasswords + " in its userPasswordsCollection field has a non-nullable userID field.");
            }
            Collection<Vehicles> vehiclesCollectionOrphanCheck = users.getVehiclesCollection();
            for (Vehicles vehiclesCollectionOrphanCheckVehicles : vehiclesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Vehicles " + vehiclesCollectionOrphanCheckVehicles + " in its vehiclesCollection field has a non-nullable userID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Managers managerByID = users.getManagerByID();
            if (managerByID != null) {
                managerByID.getUsersCollection().remove(users);
                managerByID = em.merge(managerByID);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
