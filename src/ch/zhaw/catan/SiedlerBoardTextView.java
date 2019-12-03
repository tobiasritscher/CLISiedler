package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoardTextView;

public class SiedlerBoardTextView extends HexBoardTextView<Land, String, Road, String> {

    public SiedlerBoardTextView(SiedlerBoard board) {
        super(board);
    }

}
