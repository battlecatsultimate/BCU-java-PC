package page.support;

import common.io.Backup;

import java.util.Comparator;

public class DateComparator implements Comparator<Backup> {
    @Override
    public int compare(Backup o1, Backup o2) {
        return Long.compare(Long.parseLong(o2.getName().replaceAll("[:\\-/]", "")), Long.parseLong(o1.getName().replaceAll("[:\\-/]", "")));
    }
}