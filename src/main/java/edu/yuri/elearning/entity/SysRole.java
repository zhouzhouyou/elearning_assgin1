package edu.yuri.elearning.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<SysUser> users;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<SysAuth> auths;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SysUser> getUsers() {
        return users;
    }

    public List<SysAuth> getAuths() {
        return auths;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysRole.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }

    public SysRole() {
    }
}
