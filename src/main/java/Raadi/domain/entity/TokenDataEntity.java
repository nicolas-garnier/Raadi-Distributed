package Raadi.domain.entity;

import java.util.ArrayList;

/**
 * Token data entity class.
 */
public class TokenDataEntity {

    /**
     * Attributes/
     */
    private Double weight;
    private ArrayList<Integer> positions;

    /**
     * Token data entity constructor.
     * @param weight Data's weight.
     * @param positions Data's position.
     */
    public TokenDataEntity(Double weight, ArrayList<Integer> positions) {
        this.weight = weight;
        this.positions = positions;
    }

    /**
     * Getter positions.
     * @return ArrayList positions
     */
    public ArrayList<Integer> getPositions() {
        return positions;
    }

    /**
     * Setter positions.
     * @param positions Data's positions.
     */
    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }

    /**
     * Getter weight.
     * @return Double.
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Setter weight
     * @param weight Data's weight.
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
