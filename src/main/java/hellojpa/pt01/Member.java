package hellojpa.pt01;

import javax.persistence.*;

//@Entity
public class Member {

    @Id @GeneratedValue
    private Long Id;

    @Column(name="USERNAME")
    private String name;

    public Member() {}

    public Member(Long id, String name) {
        Id = id;
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
