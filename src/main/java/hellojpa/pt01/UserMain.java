package hellojpa.pt01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class UserMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Users user = new Users();
            /*user.setId(2L);
            user.setUsername("B");
            user.setRoleType(RoleType.ADMIN);*/

            // @GeneratedValue(strategy = GenerationType.AUTO) 때문에 id값은 자동 생성 됨
            user.setUsername("C");

            em.persist(user);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
