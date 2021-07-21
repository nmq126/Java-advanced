package com.t2012e.hashmap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoHashMap {
    public static void main(String[] args) {
        Map<String, Teacher> teacherMap = new HashMap<>();
        String regex = "(^GV.+)\\|(.+)\\|(.+)\\|(.+)";
        Pattern pattern = Pattern.compile(regex);
        String s1 = "Mã giảng viên | Tên giảng viên | Ngày | Số lượng bánh ngọt\n" +
                "_______________________________________________________________\n" +
                "GV001       |  Nguyễn Tuân  |  06/05/2019 |    1\n" +
                "GV002       |  Đặng Kim Thi  |  06/05/2019 |    2\n" +
                "GV001       |  Nguyen Tuân  |  07/05/2019 |    2\n" +
                "GV003       |  Quang Hòa  |  07/05/2019 |    2\n" +
                "GV004       |  Văn Thuận  |  07/05/2019 |    1\n" +
                "GV005       |  Hồng Luyến  |  08/05/2019 |    5\n" +
                "GV002       |  Đặng Kim Thi  |  08/05/2019 |    1\n" +
                "GV002       |  Đặng Kim Thi  |  09/05/2019 |    1\n" +
                "GV005       |  Hồng Luyến  |  09/05/2019 |    5\n" +
                "GV001       |  Nguyễn Tuân  |  10/05/2019 |    1\n" +
                "GV003       |  Quang Hòa  |  10/05/2019 |    1\n" +
                "GV004       |  Văn Thuận  |  11/05/2019 |    1";
        String[] s2 = s1.split("\\n");
        for (String str : s2) {
            Matcher m = pattern.matcher(str);
            if (m.find()) {
                Teacher newTeacher = new Teacher();
                newTeacher.setRollNumber(m.group(1).trim());
                newTeacher.setFullName(m.group(2).trim());
                newTeacher.setDate(DateTimeUtilHashMap.parseDateFromString(m.group(3).trim()));
                newTeacher.setQuantity(Integer.parseInt(m.group(4).trim()));
                if (teacherMap.containsKey(newTeacher.getRollNumber())) {
                    Teacher teacherDuplicate = teacherMap.get(newTeacher.getRollNumber());
                    teacherDuplicate.setQuantity(teacherDuplicate.getQuantity() + newTeacher.getQuantity());
                }
                else {
                    teacherMap.put(newTeacher.getRollNumber(), newTeacher);
                }
            }
        }
        DemoHashMap.sortTeacher(teacherMap);
    }

    private static ArrayList sortTeacher(Map map) {
        ArrayList<Teacher> teacherArrayList = new ArrayList<Teacher>(map.values());
        Collections.sort(teacherArrayList, new TeacherComparator());
        for (int i = 0; i < 3; i++) {
            System.out.println(teacherArrayList.get(i).toString());
        }
        return teacherArrayList;
    }
}
