package Raadi.domain.entity;

import java.util.ArrayList;

public class TokenDataEntity {

    private Double weight;
    private ArrayList<Integer> positions;

    public TokenDataEntity(Double weight, ArrayList<Integer> positions) {
        this.weight = weight;
        this.positions = positions;
    }

    /**
     * Getter positions
     * @return ArrayList positions
     */
    public ArrayList<Integer> getPositions() {
        return positions;
    }

    /**
     * Setter positions
     * @param positions
     */
    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }

    /**
     * Getter weight
     * @return Double
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Setter weight
     * @param weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
