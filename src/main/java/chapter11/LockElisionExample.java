package chapter11;

import java.util.List;
import java.util.Vector;

public class LockElisionExample {

    public String getStoogeNames() {
        List<String> names = new Vector<>();
        names.add("Moe");
        names.add("Larry");
        names.add("Curly");
        return names.toString();
    }
}
