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
import SaveDB.ManagerPasswords;
import SaveDB.Managers;
import java.util.ArrayList;
import java.util.Collection;
import SaveDB.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.IllegalOrphanException;
import t.exceptions.NonexistentEntityException;

/**
 *
 * @author Daniel
 */
public class ManagersJpaController implements Serializable {

    public ManagersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Managers managers) {
        if (managers.getManagerPasswordsCollection() == null) {
            managers.setManagerPasswordsCollection(new ArrayList<ManagerPasswords>());
        }
        if (managers.getUsersCollection() == null) {
            managers.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ManagerPasswords> attachedManagerPasswordsCollection = new ArrayList<ManagerPasswords>();
            for (ManagerPasswords managerPasswordsCollectionManagerPasswordsToAttach : managers.getManagerPasswordsCollection()) {
                managerPasswordsCollectionManagerPasswordsToAttach = em.getReference(managerPasswordsCollectionManagerPasswordsToAttach.getClass(), managerPasswordsCollectionManagerPasswordsToAttach.getId());
                attachedManagerPasswordsCollection.add(managerPasswordsCollectionManagerPasswordsToAttach);
            }
            managers.setManagerPasswordsCollection(attachedManagerPasswordsCollection);
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : managers.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            managers.setUsersCollection(attachedUsersCollection);
            em.persist(managers);
            for (ManagerPasswords managerPasswordsCollectionManagerPasswords : managers.getManagerPasswordsCollection()) {
                Managers oldManagerIDOfManagerPasswordsCollectionManagerPasswords = managerPasswordsCollectionManagerPasswords.getManagerID();
                managerPasswordsCollectionManagerPasswords.setManagerID(managers);
                managerPasswordsCollectionManagerPasswords = em.merge(managerPasswordsCollectionManagerPasswords);
                if (oldManagerIDOfManagerPasswordsCollectionManagerPasswords != null) {
                    oldManagerIDOfManagerPasswordsCollectionManagerPasswords.getManagerPasswordsCollection().remove(managerPasswordsCollectionManagerPasswords);
                    oldManagerIDOfManagerPasswordsCollectionManagerPasswords = em.merge(oldManagerIDOfManagerPasswordsCollectionManagerPasswords);
                }
            }
            for (Users usersCollectionUsers : managers.getUsersCollection()) {
                Managers oldManagerByIDOfUsersCollectionUsers = usersCollectionUsers.getManagerByID();
                usersCollectionUsers.setManagerByID(managers);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldManagerByIDOfUsersCollectionUsers != null) {
                    oldManagerByIDOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldManagerByIDOfUsersCollectionUsers = em.merge(oldManagerByIDOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Managers managers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Managers persistentManagers = em.find(Managers.class, managers.getId());
            Collection<ManagerPasswords> managerPasswordsCollectionOld = persistentManagers.getManagerPasswordsCollection();
            Collection<ManagerPasswords> managerPasswordsCollectionNew = managers.getManagerPasswordsCollection();
            Collection<Users> usersCollectionOld = persistentManagers.getUsersCollection();
            Collection<Users> usersCollectionNew = managers.getUsersCollection();
            List<String> illegalOrphanMessages = null;
            for (ManagerPasswords managerPasswordsCollectionOldManagerPasswords : managerPasswordsCollectionOld) {
                if (!managerPasswordsCollectionNew.contains(managerPasswordsCollectionOldManagerPasswords)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ManagerPasswords " + managerPasswordsCollectionOldManagerPasswords + " since its managerID field is not nullable.");
                }
            }
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its managerByID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ManagerPasswords> attachedManagerPasswordsCollectionNew = new ArrayList<ManagerPasswords>();
            for (ManagerPasswords managerPasswordsCollectionNewManagerPasswordsToAttach : managerPasswordsCollectionNew) {
                managerPasswordsCollectionNewManagerPasswordsToAttach = em.getReference(managerPasswordsCollectionNewManagerPasswordsToAttach.getClass(), managerPasswordsCollectionNewManagerPasswordsToAttach.getId());
                attachedManagerPasswordsCollectionNew.add(managerPasswordsCollectionNewManagerPasswordsToAttach);
            }
            managerPasswordsCollectionNew = attachedManagerPasswordsCollectionNew;
            managers.setManagerPasswordsCollection(managerPasswordsCollectionNew);
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            managers.setUsersCollection(usersCollectionNew);
            managers = em.merge(managers);
            for (ManagerPasswords managerPasswordsCollectionNewManagerPasswords : managerPasswordsCollectionNew) {
                if (!managerPasswordsCollectionOld.contains(managerPasswordsCollectionNewManagerPasswords)) {
                    Managers oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords = managerPasswordsCollectionNewManagerPasswords.getManagerID();
                    managerPasswordsCollectionNewManagerPasswords.setManagerID(managers);
                    managerPasswordsCollectionNewManagerPasswords = em.merge(managerPasswordsCollectionNewManagerPasswords);
                    if (oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords != null && !oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords.equals(managers)) {
                        oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords.getManagerPasswordsCollection().remove(managerPasswordsCollectionNewManagerPasswords);
                        oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords = em.merge(oldManagerIDOfManagerPasswordsCollectionNewManagerPasswords);
                    }
                }
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Managers oldManagerByIDOfUsersCollectionNewUsers = usersCollectionNewUsers.getManagerByID();
                    usersCollectionNewUsers.setManagerByID(managers);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldManagerByIDOfUsersCollectionNewUsers != null && !oldManagerByIDOfUsersCollectionNewUsers.equals(managers)) {
                        oldManagerByIDOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldManagerByIDOfUsersCollectionNewUsers = em.merge(oldManagerByIDOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = managers.getId();
                if (findManagers(id) == null) {
                    throw new NonexistentEntityException("The managers with id " + id + " no longer exists.");
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
            Managers managers;
            try {
                managers = em.getReference(Managers.class, id);
                managers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The managers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ManagerPasswords> managerPasswordsCollectionOrphanCheck = managers.getManagerPasswordsCollection();
            for (ManagerPasswords managerPasswordsCollectionOrphanCheckManagerPasswords : managerPasswordsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Managers (" + managers + ") cannot be destroyed since the ManagerPasswords " + managerPasswordsCollectionOrphanCheckManagerPasswords + " in its managerPasswordsCollection field has a non-nullable managerID field.");
            }
            Collection<Users> usersCollectionOrphanCheck = managers.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Managers (" + managers + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable managerByID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(managers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Managers> findManagersEntities() {
        return findManagersEntities(true, -1, -1);
    }

    public List<Managers> findManagersEntities(int maxResults, int firstResult) {
        return findManagersEntities(false, maxResults, firstResult);
    }

    private List<Managers> findManagersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Managers.class));
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

    public Managers findManagers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Managers.class, id);
        } finally {
            em.close();
        }
    }

    public int getManagersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Managers> rt = cq.from(Managers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
