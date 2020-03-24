package tongge;

import java.util.*;

/**
 * @author fourous
 * @date: 2020/3/22
 * @description: HashSet例程
 */
public class HashSetTest {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        System.out.println(hashSet.contains(1));
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        for (Integer num : hashSet) {
            System.out.println(num);
        }
        HashSet<User> hashSetUser = new HashSet<>();
        hashSetUser.add(new User("1", "2"));
        hashSetUser.add(new User("1", "2"));
        // 这里可以看到注释HashCode会导致这里是两个对象
        System.out.println(hashSetUser.size());
    }
}

class User {
    public String firstName;
    public String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        } else {
            User user = (User) object;
            return this.firstName.equals(user.firstName) && this.lastName.equals(user.lastName);
        }

    }

    /**
     * 这里可以尝试注释这个HashCode，会发现Set的元素是两个，没有去重
     * 一个是HashSet 比较是基于HashMap的，而对象来自于Object，Object比较先比较HashCode，再去Equal对象属性
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
