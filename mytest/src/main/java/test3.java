
import java.util.*;

public class test3 {
    public static void main(String[] args) {
        String string = "10001,张无忌,男,2023-07-22 11:11:12,东湖-黄鹤楼#10002,赵敏,女,2023-07-22 09:11:21,黄鹤楼-归元禅寺#10003,周芷若,女,2023-07-22 04:11:21,木兰文化区-东湖#10004,小昭,女,2023-07-22 08:11:21,东湖#10005,灭绝,女,2023-07-22 17:11:21,归元禅寺";
        String[] split = string.split("#");

        Set set = new HashSet();
        for (String s : split) {
            String[] split1 = s.split(",");
            Student student = new Student();
            student.id = split1[0];
            student.name = split1[1];
            student.sex = split1[2];
            student.time = split1[3];
            String[] split2 = split1[4].split("-");
            student.address = new String[split2.length];
            for (int i = 0; i < split2.length; i++) {
                student.address[i] = split2[i];
            }

            set.add(student);
        }
        System.out.println(set);
        //打印set id name 等
        for (Object o : set) {
            Student student = (Student) o;
            System.out.print(student.id + " " + student.name + " " + student.sex + " " + student.time);
            for (int i = 0; i < student.address.length; i++) {
                System.out.print(" " + student.address[i]);
            }
            System.out.println();
        }

        Map map = new HashMap();
        for (Object o : set) {
            Student student = (Student) o;
            for (int i = 0; i < student.address.length; i++) {
                if (!map.containsKey(student.address[i])) {
                    map.put(student.address[i], 1);
                } else {
                    map.put(student.address[i], (Integer) map.get(student.address[i]) + 1);
                }
            }
        }

        System.out.println(map);

        //找出访问次数最多的
        Set entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        Map.Entry entry = (Map.Entry) iterator.next();
        int max = (Integer) entry.getValue();
        String maxAddress = (String) entry.getKey();
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            if ((Integer) entry.getValue() > max) {
                max = (Integer) entry.getValue();
                maxAddress = (String) entry.getKey();
            }
        }

        for (Object o : set) {
            Student student = (Student) o;
            int count = 0;
            for (int i = 0; i < student.address.length; i++) {
                if (student.address[i].equals(maxAddress)) {
                    count++;
                }
            }
            if (count == 0) {
                System.out.println(student.id + " " + student.name + " " + student.sex + " " + student.time);
            }
        }
    }

    public static class Student {
        private String id;
        private String name;
        private String sex;
        //搜集时间
        private String time;
        private String[] address;

    }
}
