package models;

public enum AccountIndex {
    USERNAME(0),
    PASSWORD(1),
    ROLE(2);

    private final int index;

    private AccountIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }
}
