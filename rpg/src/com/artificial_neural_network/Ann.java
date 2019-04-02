package com.artificial_neural_network;

import java.util.Random;

import com.artificial_neural_network.Neuron;

public class Ann {
	public Neuron neuron1;
	public Neuron neuron2;
	public Neuron neuron3;
	public Neuron neuron4;
	
	public double e, f;
	
	Random random;
	
	public Ann() {
		random = new Random();
		e = 1;
		f = 1;
		
		//first layer
		neuron1 = new Neuron();
		neuron2 = new Neuron();
		
		//second layer
		neuron3 = new Neuron();
		neuron4 = new Neuron();
		
		//input values for first layer neurons
		neuron2.assignInputValue(random.nextDouble());
		//set this ^ to player's average hit rate
		
		//neuron connections
		neuron1.connect(0.5, neuron1, neuron3);
		neuron2.connect(0.5, neuron2, neuron4);
		neuron1.connect(0.5, neuron1, neuron4);
		neuron2.connect(0.5, neuron2, neuron3);
	}
	
	public void assignAverageDamage(int recentlyDelt, int justDelt, int timesDelt) {
		double second = 0;
		neuron1.assignInputValue((((recentlyDelt+justDelt)/(timesDelt+1)))*0.1);
		second = Math.floor(neuron1.inputValue);
		neuron1.inputValue -= second;
		
		calculateOutput();
	}
	
	public void calculateOutput() {
		neuron1.outputValue1 = ((neuron1.inputValue*neuron1.connectionWeight[0]) + (neuron2.inputValue*neuron2.connectionWeight[1]));	// <- this is sigma
		e = e * neuron1.outputValue1;
		e = (1/(1+Math.pow(e, -neuron1.outputValue1)));
		
		neuron2.outputValue1 = ((neuron2.inputValue*neuron2.connectionWeight[0]) + (neuron1.inputValue*neuron1.connectionWeight[1]));
		f = f * neuron2.outputValue1;
		f = (1/(1+Math.pow(f, -neuron2.outputValue1)));
	}
}
