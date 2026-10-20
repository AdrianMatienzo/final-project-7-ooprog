package strategy;

import java.util.Comparator;

public class SortByAge implements StudentSortStrategy {

    private boolean ascending;

    public SortByAge(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Comparator<String> getComparator() {
        Comparator<String> comparator = Comparator.comparingInt(
            line -> Integer.parseInt(line.split("\\|", -1)[2].trim())
        );

        return ascending ? comparator : comparator.reversed();
    }
}
