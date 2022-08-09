package hellojpa;

import jdk.swing.interop.SwingInterOpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        // persistenceUnitName은 persistence.xml의 persistence-unit name=""의 이름이다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // 생성
            /*Member member = new Member();
            member.setId(1L);
            member.setName("helloA");
            em.persist(member);*/

            // 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.Id = " + findMember.getId());
            System.out.println("findMember.Name = " + findMember.getName());

            // 수정 : 따로 저장할 필요 없이 값을 가져온 순간부터 JPA가 관리하기 때문에 값을 변경하면 JPA가 알아서 저장한다.
            findMember.setName("HelloJPA");

            // 삭제
            em.remove(findMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
