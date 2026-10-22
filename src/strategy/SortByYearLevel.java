package strategy;

import java.util.Comparator;
import models.StudentIndex;

public class SortByYearLevel implements StudentSortStrategy {

    private boolean ascending;

    public SortByYearLevel(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Comparator<String> getComparator() {
        Comparator<String> comparator = Comparator.comparingInt(line -> {
            String yearLevel = line.split("\\|", -1)[StudentIndex.YEAR_LEVEL.getIndex()].trim();

            return Integer.parseInt(
                yearLevel.split("\\s+")[0].replaceAll("\\D", "")
            );
        });

        return ascending ? comparator : comparator.reversed();
    }
}
