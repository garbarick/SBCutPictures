package ru.net.serbis.cut.pictures.param;

import android.graphics.*;

public enum SaveImgFormat
{
    jpg(Bitmap.CompressFormat.JPEG),
    png(Bitmap.CompressFormat.PNG);

    private Bitmap.CompressFormat format;

    private SaveImgFormat(Bitmap.CompressFormat format)
    {
        this.format = format;
    }

    public Bitmap.CompressFormat getFormat()
    {
        return format;
    }
    
    public int getQuality()
    {
        switch (this)
        {
            case jpg:
                return Params.JPG_QUALITY.getValue();
            default:
                return 100;
        }
    }
}
