package org.jorry.netty08;

import java.io.Serializable;

/**
 * @author :Jorry
 * @date : 2023-06-25 12:14
 * @Describe: 类的描述信息
 */
public class User implements Serializable {
    private Integer id;

    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
