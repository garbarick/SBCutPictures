package ru.net.serbis.cut.pictures.param;

import ru.net.serbis.cut.pictures.*;
import ru.net.serbis.utils.param.*;

public class ImageTypeParam extends SpinnerParam<SaveImgFormat>
{
    public ImageTypeParam()
    {
        super(R.string.image_format, SaveImgFormat.jpg, SaveImgFormat.class.getEnumConstants());
    }

    @Override
    public String typeToString(SaveImgFormat value)
    {
        return value.name();
    }

    @Override
    public SaveImgFormat stringToType(String value)
    {
        return SaveImgFormat.valueOf(value);
    }
}
