package net.librec.util;

public class MemoryUtil {

	public MemoryUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void getTotalMemory()
	{
		long heapMaxSize = Runtime.getRuntime().totalMemory();
        String size = "" + heapMaxSize;
        if (heapMaxSize < 1024) size = heapMaxSize + " B";
        else
        {
        	int z = (63 - Long.numberOfLeadingZeros(heapMaxSize)) / 10;
        	size = String.format("%.1f %sB", (double)heapMaxSize / (1L << (z*10)), " KMGTPE".charAt(z));
        }
        
        System.out.println("Total memory = " + size);
	}
	
	public static void getMaxMemory()
	{
		long heapMaxSize = Runtime.getRuntime().maxMemory();
        String size = "" + heapMaxSize;
        if (heapMaxSize < 1024) size = heapMaxSize + " B";
        else
        {
        	int z = (63 - Long.numberOfLeadingZeros(heapMaxSize)) / 10;
        	size = String.format("%.1f %sB", (double)heapMaxSize / (1L << (z*10)), " KMGTPE".charAt(z));
        }
        
        System.out.println("Max memory = " + size);
	}
	
	public static void main(String[] args)
	{
		getMaxMemory();
	}

}
