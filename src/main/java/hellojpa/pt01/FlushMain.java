package hellojpa.pt01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class FlushMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {

            // 영속
            Member member = new Member(200L, "member200");
            em.persist(member);

            // flush()를 사용하면 commit()전에 데이터베이스에 insert 됨 - 1차 캐시도 유지 됨
            em.flush();

            System.out.println("==============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
