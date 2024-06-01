package ru.net.serbis.cut.pictures.param;

import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.cut.pictures.util.*;

public interface Params
{
    FileParam SOURCE_FOLDER = new FileParam(R.string.source_folder, IOTool.get().getDownloadPath(), true, false);
    FileParam BACKUP_FOLDER = new FileParam(R.string.backup_folder, IOTool.get().getExternalFile("backups/" + Constants.APP), true, false);
    EditNumberParam CUT_WIDTH = new EditNumberParam(R.string.cut_width, 1351);
    EditNumberParam CUT_HEIGHT = new EditNumberParam(R.string.cut_height, 760);
    EditTextParam NAME_PATTERN = new EditTextParam(R.string.name_pattern, "yyyy-MM-dd_HH-mm-ss");
    ImageTypeParam IMAGE_FORMAT = new ImageTypeParam();
    StringsParam CATEGORIES = new StringsParam(R.string.categories);
    EditNumberParam POS = new EditNumberParam(R.string.pos, 0, false);
    SeekBarParam JPG_QUALITY = new SeekBarParam(R.string.jpg_quality, 100, 80);
    EditTextParam CATEGORY = new EditTextParam(R.string.category, "");
    
    Param[] PARAMS = new Param[]{
        SOURCE_FOLDER,
        BACKUP_FOLDER,
        CUT_WIDTH,
        CUT_HEIGHT,
        NAME_PATTERN,
        IMAGE_FORMAT,
        JPG_QUALITY,
        CATEGORIES
    };
}
