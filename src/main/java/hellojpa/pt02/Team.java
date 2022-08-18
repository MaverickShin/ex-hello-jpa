package hellojpa.pt02;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // appedBy = "team"는 Member 객체에서 해당 객체(private Team team)를 선언한 멤버변수의 이름
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
