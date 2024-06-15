package ru.net.serbis.cut.pictures.sort;

import java.io.*;
import java.util.*;

public class ImageSorter implements Comparator<File>
{
    @Override
    public int compare(File p1, File p2)
    {
        Long l1 = p1.lastModified();
        Long l2 = p2.lastModified();
        int p = l1.compareTo(l2);
        if (p != 0)
        {
            return p;
        }
        return p1.compareTo(p2);
    }
}
