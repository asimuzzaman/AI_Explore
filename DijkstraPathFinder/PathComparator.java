//package dijkstra;

import java.util.Comparator;

public class PathComparator implements Comparator<Path>
{
    @Override
    public int compare(Path x, Path y)
    {
        if (x.weight < y.weight)
        {
            return -1;
        }
        if (x.weight > y.weight)
        {
            return 1;
        }
        return 0;
    }
}
