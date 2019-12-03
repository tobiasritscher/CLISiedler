package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;


public class SiedlerBoard extends HexBoard<Config.Land, String, Road, String> {
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
}
