package Raadi.domain.model;

import java.util.ArrayList;

public class TokenData {

    private Double weight;
    private ArrayList<Integer> positions;

    public TokenData(Double weight, ArrayList<Integer> positions) {
        this.weight = weight;
        this.positions = positions;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
