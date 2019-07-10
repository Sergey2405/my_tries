package myPackage;

import java.util.*;
//import org.junit.*;
//import static org.junit.Assert.*;

public class MyTrie implements Cloneable{
    private MyTrie[] childs;
    private int depth;
    private int currentDepth;
    private int numChilds;
    private int[] parentPath;
    MyTrie parentMyTrie;
    private int value;
    
    MyTrie(int depth
          ,int numChilds){
        initMyTrie(depth, currentDepth, numChilds, this);
        for(int i = 0; i < depth - 1; i++)
            this.parentPath[i] = -1;
        MyTrie(depth, 0, numChilds, this, 0);
    }

    MyTrie(int depth
          ,int currentDepth
          ,int numChilds
          ,MyTrie parentMyTrie
          ,int parentIndex){
        MyTrie(depth, currentDepth, numChilds
              ,parentMyTrie, parentIndex);
    }
    
    private MyTrie MyTrie(int depth
                         ,int currentDepth
                         ,int numChilds
                         ,MyTrie parentMyTrie
                         ,int parentIndex){
        if(currentDepth != 0)
            initMyTrie(depth, currentDepth, numChilds, parentMyTrie);
        int[] tokenParentPath = parentMyTrie.getParentPath();
        for(int i = 0; i < depth - 1; i++)
            this.parentPath[i] = tokenParentPath[i];
        if(currentDepth == depth) return this;
        else
            if(this.currentDepth != 0)
                this.parentPath[this.currentDepth - 1] = parentIndex;

        // String pathToPrint = ""; // to be removed
        
        for (int i = 0; i < numChilds; i++){
            MyTrie tmpMyTrie;
            this.childs[i] = new MyTrie(depth
                                       ,currentDepth + 1
                                       ,numChilds
                                       ,this
                                       ,i);
        }

        //print once again. to be removed
        // pathToPrint = "";
        // for(int i = 0; i < this.depth - 1; i++)
            // pathToPrint += " " + String.valueOf(this.parentPath[i]);
        // System.out.println("MyTrie:"
                         // + " currentDepth=" + this.currentDepth
                         // + " numChilds=" + this.numChilds
                         // + " value=" + this.value
                         // + " parentPath=[" + pathToPrint + "]"
                         // + " this.childs " + this.childs);

        return this;
    }
    
    private void initMyTrie(int depth
                          ,int currentDepth
                          ,int numChilds
                          ,MyTrie parentMyTrie){
        this.depth = depth;
        this.currentDepth = currentDepth;
        this.numChilds = numChilds;
        this.childs = new MyTrie[numChilds];
        this.parentPath = new int[depth - 1];
        this.parentMyTrie = parentMyTrie;
        this.value = 0;
    }

    public MyTrie getMyTrie(int[] tokenParentPath){
        if((this.currentDepth < this.depth - 1)
            &&(this.currentDepth >= 0)
            &&(tokenParentPath[this.currentDepth] >= 0)){
                return this.childs[tokenParentPath[this.currentDepth]]
                           .getMyTrie(tokenParentPath);
        }
        else{
            return this;
        }
    }

    public int[] getParentPath(){
        return this.parentPath;
    }

    public int getValue(){
        return this.value;
    }

    public MyTrie getMyParentTrie(){
        return this.parentMyTrie;
    }
    
    public MyTrie[] getMyChilds(){
        return this.childs;
    }


    public void setValue(int value){
        this.value = value;
    }
    
    public void printParentPath()
    {
        String pathToPrint = String.valueOf(this.parentPath[0]);
        for(int i = 1; i < this.depth - 1; i++)
            pathToPrint += " " + String.valueOf(this.parentPath[i]);
        System.out.println("printParentPath: " + pathToPrint);
    }

    public Object clone(){
        Object retVal;
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            return null;
        }
    }

    public static void main(String[] args)
    {
        MyTrie myTrie = new MyTrie(Integer.parseInt(args[0])
                                  ,Integer.parseInt(args[1]));
        //myTrie.printParentPath();
        int[] myTokenParentPath= new int[5];
        myTokenParentPath[0] = 0;
        myTokenParentPath[1] = 1;
        myTokenParentPath[2] = 2;
        myTokenParentPath[3] = -1;
        MyTrie myTokenTrie, myParentTokenTrie, myChildTokenMyTrie ;
        
        myTokenTrie = myTrie.getMyTrie(myTokenParentPath);
        myTokenTrie.setValue(666);
        
        System.out.println("myTokenTrie: value=" + myTokenTrie.getValue());
        myTokenTrie.printParentPath();
        
        myTokenTrie.getMyParentTrie().setValue(333);
        myTokenTrie.getMyChilds()[2].setValue(999);
        
        myTokenParentPath[2] = -1;
        myParentTokenTrie = myTrie.getMyTrie(myTokenParentPath);
        
        myTokenParentPath[2] = 2;
        myTokenParentPath[3] = 2;
        myTokenParentPath[4] = -1;
        
        myChildTokenMyTrie = myTrie.getMyTrie(myTokenParentPath);
        
        System.out.println("myParentTokenTrie: value=" + myParentTokenTrie.getValue());
        myParentTokenTrie.printParentPath();
        System.out.println("myChildTokenMyTrie: value=" + myChildTokenMyTrie.getValue());
        myChildTokenMyTrie.printParentPath();
    }
}