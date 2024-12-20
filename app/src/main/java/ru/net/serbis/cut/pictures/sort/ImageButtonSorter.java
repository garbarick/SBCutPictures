package ru.net.serbis.cut.pictures.sort;

import android.widget.*;
import java.util.*;

public class ImageButtonSorter implements Comparator<ImageButton>
{
    @Override
    public int compare(ImageButton p1, ImageButton p2)
    {
        return p1.getContentDescription().toString().compareTo(p2.getContentDescription().toString());
    }
}
