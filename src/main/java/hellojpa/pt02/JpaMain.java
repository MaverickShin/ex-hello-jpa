package hellojpa.pt02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // persistenceUnitName은 persistence.xml의 persistence-unit name=""의 이름이다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // 저장
            Team team = new Team();
            team.setName("TeamA");

            // -> mappedBy가 붙으면 읽기 전용이 되어서 JPA가 실행될 때 해당 값만 insert 되지 않음 (무시됨)
            // team.getMembers().add(member);

            em.persist(team);

            Member member =new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

//           team.getMembers().add(member);
//           em.flush();
//           em.clear();

            // mappedBy 때문에 읽기 전용이니까 값을 안넣어줘도 되느냐? 그건 아니다..
            // mappedBy의 역할을 데이터베이스 PK-FK 관점으로 생각하면 안넣어줘도 되는게 맞지만
            // 객체지향 적으로 생각해보면 team 객체에도 값을 넣어 주어야 한다.

            /**
             * 정리하자면..
             *
             * 아래 조회의 성공/실패 이유
             *
             * 1.team.getMembers().add(member)와 plush()를 둘다 사용하지 않았을 경우
             *  아직 DB에 insert되지 않았기 때문에 team의 id는 아직 자동 생성되지 않았다.
             *  그렇기 때문에 지금 team.getId()를 해봤자 team 변수에는 id값이 아무것도 없다.
             *  그리고 DB에서 조회된 값이 없더라도 team 객체에 member값을 넘겨준 적이 없어서
             *  객체 관계로도 team객체를 통해 member값을 조회할 수 없는것이다.
             *
             * 2.team.getMembers().add(member)로 team 객체에 member값을 넘겨주지 않을 거면
             *   flush()를 통해 저장(insert)쿼리를 먼저 실행하여 team 객체가 PK 값을 가져야 의도한 select가 가능하다.
             *
             * 3.flush()를 사용하지 않을거면 team 객체에는 아직 아무값도 없기 때문에 (tx.commit()이 발생하기 전엔 insert 실행 안됨)
             *   team.getMembers().add(member);처럼 객체 관계 통해 Team 객체에서 Member를 조회할 수 있게 team 객체에도 값을 넣어주어야 한다.
             *
             * 4.하지만 team.getMembers().add(member);은 사람이 작업하다 보면 빼먹는 실수를 할 수 있기 때문에..
             *   연과관계 편의 메소드를 만들어두면 실수를 없앨 수 있고 편의성도 좋아진다..
             *   Member클래스에 setTeam() 메소드대신 (lombok을 쓰면 setter, getter 메소드가 없을 수 있기 때문에)
             *   changeTeam(Team team)메소드를 만들어서
             *   this.team = team; 과
             *   team.getMembers().add(this);를 생성해두자..
             *   혹은 Team 클래스에 addMember(Member member) 메소드를 생성해서
             *   member.setTeam(this); 와
             *   members.add(member); 를 생성해도 된다..
             *   둘 중 하나만 써야하고 어느것이 어울릴지는 상황에 따라 다를 수 있다.
             */

            // 조회
            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시에서 조회 됨
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
