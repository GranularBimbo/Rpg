package com.artificial_neural_network;

public class Neuron {
	public Neuron connections[];
	public double connectionWeight[];
	public double inputValue, outputValue1, outputValue2;
	
	public Neuron() {
		connections = new Neuron[2];
		connectionWeight = new double[2];
	}
	
	public void connect(double weight, Neuron startingNeuron, Neuron targetNeuron) {
		for(int i = 0; i < connections.length; i++) {
			if(startingNeuron.connections[i] == null) {
				startingNeuron.connections[i] = targetNeuron;
				startingNeuron.connectionWeight[i] = weight;
				
				if(targetNeuron.connections[i] == null) {
					connect(0.5,targetNeuron,startingNeuron);
				}
				
				break;
			}
		}
	}
	
	public void assignInputValue(double value) {	//only for first layer neurons
		this.inputValue = value;
	}
}
