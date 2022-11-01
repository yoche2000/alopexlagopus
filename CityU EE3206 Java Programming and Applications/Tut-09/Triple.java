
public class Triple<E1, E2, E3>
{
    private E1 first;
    private E2 second;
    private E3 third;
    
    public Triple(E1 v1, E2 v2, E3 v3)
    {
        first = v1;
        second = v2;
        third = v3;
    }
    
    public E1 getFirst()
    {
        return first;
    }
    
    public E2 getSecond()
    {
        return second;
    }
    
    public E3 getThird()
    {
        return third;
    }
    
    public void setFirst(E1 v)
    {
        first = v;
    }
    
    public void setSecond(E2 v)
    {
        second = v;
    }
    
    public void setThird(E3 v)
    {
        third = v;
    }
}
