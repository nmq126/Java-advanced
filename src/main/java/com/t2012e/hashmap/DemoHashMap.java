package com.t2012e.hashmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoHashMap {
    public static void main(String[] args) {
        Map<String, Teacher> teacherMap = new HashMap<>();
        String regex = "(^GV.+)\\|(.+)\\|(.+)\\|(.+)";
        Pattern pattern = Pattern.compile(regex);
        //read file text
        try {
            File file = new File("src/main/java/com/t2012e/hashmap/input.text");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String str = reader.nextLine();
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
                    } else {
                        teacherMap.put(newTeacher.getRollNumber(), newTeacher);
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
