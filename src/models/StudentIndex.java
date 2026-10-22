package models;

public enum StudentIndex {
    ID(0),
    NAME(1),
    AGE(2),
    YEAR_LEVEL(3),
    COURSE(4),
    STATUS(5);

    private final int index;

    private StudentIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }
}
