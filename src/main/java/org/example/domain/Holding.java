package org.example.domain;

public class Holding {

    private long id;
    private String name;
    private Size size;

    public Holding(){}

    public Holding(long id, String name, Size size){
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getDurationInGrains(){
        return size.durationInGrains;
    }

    @Override
    public String toString() {
        return name;
    }

    public static enum Size {
        SMALL(4),
        MEDIUM(8),
        LARGE(16);

        private final int durationInGrains;

        Size(int durationInGrains){
            this.durationInGrains = durationInGrains;
        }

    }
}
