package hellojpa.pt01;

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
            member.setId(101L);
            member.setName("helloA");
            em.persist(member);
            tx.commit(); */

            // 조회
            /*Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.Id = " + findMember.getId());
            System.out.println("findMember.Name = " + findMember.getName());*/

            // 수정 : 따로 저장할 필요 없이 값을 가져온 순간부터 JPA가 관리하기 때문에 값을 변경하면 JPA가 알아서 저장한다.
            //findMember.setName("HelloJPA");

            // 삭제
            //em.remove(findMember);


            // 영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            // 1차 캐시에 저장 됨
            em.persist(member1);
            em.persist(member2);
            // 여기까지 INSERT SQL을 데이터 베이스에 보내지 않음

            // 1차 캐시에서 조회 함 (DB에서 조회하지 않음)
            Member memberA = em.find(Member.class, 150L);
            Member memberB = em.find(Member.class, 150L);

            // 영속 엔티티의 동일성 보장
            System.out.println("memberA == memberB ? " + (memberA == memberB));

            // 변경 감지 : 따로 update sql 작업없이 가져온 데이터를 저장한 객체에서 수정하면 자동으로 변경 됨
            Member memberUpdate = em.find(Member.class, 150L);
            memberUpdate.setName("ZZZZ"); // -> update 쿼리가 날라감

            // 커밋하는 순간 데이터베이스에 INSERT SQL을 보냄 : 쓰기 지연
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
