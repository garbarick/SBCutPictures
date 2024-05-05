package ru.net.serbis.cut.pictures;

import java.util.*;

public interface Constants
{
    String THROWABLE = "THROWABLE";
    String TITLE = "TITLE";
    int ERROR_LOAD_FILE_LIST = 401;
    List<String> EXTENSIONS = Arrays.asList
    (
        new String[]
        {
            "png",
            "jpg",
            "jpeg",
			"gif"
        }
	);
}
