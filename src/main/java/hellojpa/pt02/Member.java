package hellojpa.pt02;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMBER_ID")
    private Long Id;

    @Column(name = "USERNAME")
    private String username;

    // 데이터베이스 상 FK가 되는쪽이 연관관계의 주인이어햐 하고, 주인은 mappedBy를 사용하면 안된다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 연관관계 편의 메소드
    }
}
