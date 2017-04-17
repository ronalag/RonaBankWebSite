package com.ronalag.ronabank.website.model;

import com.netflix.discovery.DiscoveryClient;

public class LoadBalancer {

	private static final LoadBalancer loadBalancer = new LoadBalancer();
	
	private DiscoveryClient discoveryClient;
	
	private int currentIndex = 0; 
	
	private LoadBalancer() {
		//Singleton implementation
	}
	
	
	public DiscoveryClient getDiscoveryClient() {
		return this.discoveryClient;
	}
	
	public static LoadBalancer getInstance() {		
		return loadBalancer;
	}
	
	public void nextClient() {
		if (this.discoveryClient == null) {
			return;
		}
		
		
	}
	
	public void setDiscoveryClient(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
		this.currentIndex = 0;
	}
}
