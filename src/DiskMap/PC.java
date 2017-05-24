package DiskMap;
import java.util.*;
import java.io.*;

public class PC
{
  
    TreeMap<Integer,String> tmap = new TreeMap();
  int capacity = 10;
  long heapMaxSize = Runtime.getRuntime().maxMemory();

    // Function called by producer thread
    public void produce() throws InterruptedException
    {
        int value = 0;
        while (true)
        {
            synchronized (this)
            {
           
                while (tmap.size()==capacity)
                    wait();

  

             
	    
	    for(Integer i=0;i<10;i++){
		try{	
			tmap.put(i,"AB");
			System.out.println("Adding Data to hasmap");
			if(tmap.size() >heapMaxSize){
				
			FileOutputStream fos = new FileOutputStream("hashmap.ser");
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(tmap);
			System.out.println("Added");
			oos.close();
			fos.close();
			
			notify();

		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

            
                Thread.sleep(1000);
            }
        }
    }

   
    public void consume() throws InterruptedException
    {  
        while (true)
        {
            synchronized (this)
            {
               
                
                while (tmap.size()==0)
                    wait();

                
               
			TreeMap<Integer,String> map=null;
try{
FileInputStream fis=new FileInputStream("hashmap.ser");
ObjectInputStream os=new ObjectInputStream(fis);
map=(TreeMap)os.readObject();
os.close();
fis.close();
}
catch(Exception e){
e.printStackTrace();
}
System.out.println("Deserialized HashMap..");
  // Display content using Iterator
  Set set = map.entrySet();
  Iterator iterator = set.iterator();
  while(iterator.hasNext()) {
     Map.Entry mentry = (Map.Entry)iterator.next();
     System.out.print("key: "+ mentry.getKey() + " & Value: ");
     System.out.println(mentry.getValue());



  }

                
            }
        }
    }
}
