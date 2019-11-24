package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.Map;

public class SiedlerBoard extends HexBoard<Land, String, String, String> {

    public  SiedlerBoard(SiedlerBoard board){
        Map<Point, Land> assignment = Config.getStandardLandPlacement();

        for (Map.Entry<Point, Land> entry : assignment.entrySet()) {
            board.addField(entry.getKey(), entry.getValue());
        }
    }
}
