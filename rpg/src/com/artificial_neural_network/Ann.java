package com.artificial_neural_network;

import java.util.Arrays;
import java.util.Random;

import com.artificial_neural_network.Neuron;

public class Ann {
	public Neuron neuron1;
	public Neuron neuron2;
	public Neuron neuron3;
	public Neuron neuron4;
	public Neuron decisionNode;
	public double[] woodsLocations;
	public double[] locationConnections;
	public double sum;
	public int bias;
	public double percentError;
	public double[] loc1Connections,loc2Connections,loc3Connections,loc4Connections,loc5Connections,loc6Connections,loc7Connections;
	
	public double e, f, s;
	
	Random random;
	
	public Ann() {
		random = new Random();
		e = 1;
		f = 1;
		s = 1;
		sum = 0;
		bias = 3;
		percentError = 0;
		
		//first layer
		neuron1 = new Neuron();
		neuron2 = new Neuron();
		
		//second layer
		neuron3 = new Neuron();
		neuron4 = new Neuron();
		
		//decision
		decisionNode = new Neuron();
		
		//input values for first layer neurons
		neuron2.assignInputValue(3);
		//set this ^ to player's average hit rate
		
		//neuron connections
		neuron1.connect(0.5, neuron1, neuron3);
		neuron2.connect(0.5, neuron2, neuron4);
		neuron1.connect(0.5, neuron1, neuron4);
		neuron2.connect(0.5, neuron2, neuron3);
		neuron3.connect(0.5, neuron3, decisionNode);
		neuron4.connect(0.5, neuron4, decisionNode);
		
		woodsLocations = new double[] {1,2,3,4,5,6,7};	//array has 6 slots (arrays start at 0)
		locationConnections = new double[5];	//4 possible connections (arrays start at 0) the extra slot is for your current location
		//second slot is to the left of, third slot is above, fourth slot is to the right of, fifth is below
		
		loc1Connections = new double[4];
		loc2Connections = new double[4];
		loc3Connections = new double[4];
		loc4Connections = new double[4];
		loc5Connections = new double[4];
		loc6Connections = new double[4];
		loc7Connections = new double[4];
		neuron2.outputArrayValue = new double[4];
		
		adjustLocationConnections(false,0,0,locationConnections);
	}
	
	public static boolean valueCheck(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
	
	public void assignInputs(int location,double[] connectionList) {
		neuron1.assignInputValue(woodsLocations[location]);
		neuron2.assignInputArrayValue(connectionList);
		neuron3.outputArrayValue = new double[4];
		neuron4.outputArrayValue = new double[4];
		decisionNode.outputArrayValue = new double[4];
	}
	
	public void adjustLocationConnections(boolean yesOrNo,int direction,double location,double[] connectionList) {
		if(yesOrNo) {
			connectionList[direction] = location; 
		}
		else {
			connectionList[0] = 2;
			connectionList[1] = 3;
			connectionList[2] = 4;
			connectionList[3] = 5;
		}
	}
	
	public void findSum() {
		for(int i = 0; i < decisionNode.outputArrayValue.length; i++) {
			sum += decisionNode.outputArrayValue[i];
		}
	}
	
	public void percentError(double targetValue) {
		percentError = (((decisionNode.outputValue-targetValue)/targetValue)*100);
		System.out.println("percentError: " + percentError);
	}
	
	public void adjustValues(double[] locationList) {
		if(percentError > 6 || percentError < -6) {
			//s = (1-e)*e;	- simplified derivative?
			
			//locationList[1] += Math.ceil(s);
			
			double oldError = percentError;
			double number = 1;
			
			locationList[0] += number;
			locationList[1] += number;
			locationList[2] += number;
			locationList[3] += number;
			
			if(percentError > oldError) {
				number++;
				locationList[0] -= number;
				locationList[1] -= number;
				locationList[2] -= number;
				locationList[3] -= number;
			}
			
			if(locationList[0] == locationList[1]) {
				number++;
				locationList[1] += number;
				
				if(percentError > oldError) {
					number++;
					locationList[1] -= number;
				}
			}
		}
		else {
			if(percentError > 0) {
				System.out.println("Accuracy: " + (100-percentError));
			}
			else {
				System.out.println("Accuracy: " + (100+percentError));
			}
			
			System.out.println("Location to the left of you: " + locationList[0]);
			System.out.println("Location above you: " + locationList[1]);
			System.out.println("Location to the right of you: " + locationList[2]);
			System.out.println("Location below you: " + locationList[3]);
		}
	}
	
	//ranges: 0.70+ is 4 connections; 0.50 + is 1 connection
	
	public void calculateOutput(double targetValue) {	//the higher the number the more connections it has
		for(int i = 0; i < neuron2.inputArrayValue.length; i++) {
			neuron3.outputArrayValue[i] = ((neuron1.inputValue*neuron1.connectionWeight[0]) + (neuron2.inputArrayValue[i]*neuron2.connectionWeight[1]));	// <- this is sigma
			e = e * neuron3.outputArrayValue[i];
			e = (1/(1+Math.pow(e, -neuron3.outputArrayValue[i])));
			
			neuron4.outputArrayValue[i] = ((neuron2.inputArrayValue[i]*neuron2.connectionWeight[0]) + (neuron1.inputValue*neuron1.connectionWeight[1]));
			f = f * neuron4.outputArrayValue[i];
			f = (1/(1+Math.pow(f, -neuron4.outputArrayValue[i])));
			
			decisionNode.outputArrayValue[i] = (e*neuron3.connectionWeight[2] ) + (f*neuron4.connectionWeight[2]);
		}
		
		sum = 0;
		findSum();
		//decisionNode.outputValue = (e*neuron3.connectionWeight[2] ) + (f*neuron4.connectionWeight[2]);
		decisionNode.outputValue = sum - bias;
		percentError(targetValue);
	}
}
