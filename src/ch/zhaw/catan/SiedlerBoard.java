package ch.zhaw.catan;


import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;


public class SiedlerBoard extends HexBoard<Config.Land, Settlement, Road, String> {
    private Map<Point, Label> lowerFieldLabels = new HashMap<>();
    private Map<String, Point> labelToField = new HashMap<>();

    public SiedlerBoard() {
        setFields();
        setPoints();
    }

    public void setFields() {
        Map<Point, Land> fields = Config.getStandardLandPlacement();
        for (Map.Entry<Point, Land> field : fields.entrySet()) {
            addField(field.getKey(), field.getValue());
        }
    }

    public void setPoints() {
        Label label;
        Map<Point, Integer> fields = Config.getStandardDiceNumberPlacement();
        for (Map.Entry<Point, Integer> field : fields.entrySet()) {
            label = createLabel(field.getValue());
            lowerFieldLabels.put(field.getKey(), label);
            labelToField.put(label.toString(), field.getKey());
        }
    }

    public static Label createLabel(Integer number) {
        Label label;
        char firstCharacter = Integer.toString(number).charAt(0);
        if (number.toString().length() == 2) {
            char secondCharacter = Integer.toString(number).charAt(1);
            label = new Label(firstCharacter, secondCharacter);
        } else {
            label = new Label('0', firstCharacter);
        }
        return label;
    }

    public Map<Point, Label> getLowerFieldLabels() {
        return lowerFieldLabels;
    }

    public Map<String, Point> getLabelToField() {
        return labelToField;
    }

    static boolean isFieldCoordinate(Point position) {
        boolean isYFieldCoordinateEven = (position.y - 2) % 6 == 0;
        boolean isYFieldCoordinateOdd = (position.y - 5) % 6 == 0;
        boolean isXFieldCoordinateEven = position.x % 2 == 0;
        boolean isXFieldCoordinateOdd = (position.x - 1) % 2 == 0;

        return (position.y >= 2 && position.x >= 1)
                && (isYFieldCoordinateEven && isXFieldCoordinateEven)
                || (isYFieldCoordinateOdd && isXFieldCoordinateOdd);
    }


    public boolean hasFieldFixed(Point center){
        if (!isFieldCoordinate(center)) {
            return false;
        } else{
        return true;
        }
    }
}


