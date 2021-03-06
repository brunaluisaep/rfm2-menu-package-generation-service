package com.rfm.packagegeneration.utility;

public class Pair<K, V> {
    private  K element0;
    private  V element1;
    
    
    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<>(element0, element1);
    }
    
    public Pair() {
		super();
		// TODO Auto-generated constructor stub
	}
    

	public void setElement0(K element0) {
		this.element0 = element0;
	}

	public void setElement1(V element1) {
		this.element1 = element1;
	}

	public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    public K getElement0() {
        return element0;
    }
    
    public V getElement1() {
        return element1;
    }

	
}