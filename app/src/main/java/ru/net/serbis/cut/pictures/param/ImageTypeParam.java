package ru.net.serbis.cut.pictures.param;

import ru.net.serbis.cut.pictures.*;

public class ImageTypeParam extends SpinnerParam<String>
{
    public ImageTypeParam()
    {
        super(R.string.image_format, Constants._EXTENSIONS[0], Constants._EXTENSIONS);
    }

    @Override
    public String typeToString(String value)
    {
        return value;
    }

    @Override
    public String stringToType(String value)
    {
        return value;
    }
}
