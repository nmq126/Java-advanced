package com.t2012e.hashmap;


import java.util.Comparator;

public class TeacherComparator implements Comparator<Teacher> {

    @Override
    public int compare(Teacher o1, Teacher o2) {
        if (o1.getQuantity() != o2.getQuantity()) {
            return -(o1.getQuantity() - o2.getQuantity()); // quantity lớn hơn lên trên
        } else {
            return o1.getDate().compareTo(o2.getDate()); // ngày nhỏ hơn lên trên
        }
    }
}
