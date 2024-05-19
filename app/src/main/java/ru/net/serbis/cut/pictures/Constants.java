package ru.net.serbis.cut.pictures;

import java.util.*;

public interface Constants
{
    String APP = "SBCutPictures";
    String THROWABLE = "THROWABLE";
    String TITLE = "TITLE";
    int ERROR_LOAD_FILE_LIST = 401;
    String[] _EXTENSIONS = new String[]
    {
        "png",
        "jpg",
        "jpeg",
        "gif"
    };
    List<String> EXTENSIONS = Arrays.asList(_EXTENSIONS);
}
