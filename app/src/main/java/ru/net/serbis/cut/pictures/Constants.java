package ru.net.serbis.cut.pictures;

import java.util.*;

public interface Constants
{
    String APP = "SBCutPictures";
    int ERROR_LOAD_FILE_LIST = 401;
    int ERROR_SAVE_FILE = 402;
    int ERROR_MOVE_FILE = 403;
    int ERROR_DELETE_FILE = 404;
    String[] _EXTENSIONS = new String[]
    {
        "png",
        "jpg",
        "jpeg",
        "gif",
        "webp"
    };
    List<String> EXTENSIONS = Arrays.asList(_EXTENSIONS);
}
