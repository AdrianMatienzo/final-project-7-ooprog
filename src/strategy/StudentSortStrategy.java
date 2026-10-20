package strategy;

import java.util.Comparator;

public interface StudentSortStrategy {
    Comparator<String> getComparator();
}