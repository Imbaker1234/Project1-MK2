package com.util;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class NodeToArray {
	
	/**
	 * Utility method that converts an ArrayNode to a String[]
	 * 
	 * @param rootNode
	 * @return
	 */

	public static String[] convert(ArrayNode rootNode) {

		String[] array = new String[rootNode.size()];

		for (int i = 0; i < rootNode.size(); i++) {
			array[i] = rootNode.get(i).asText();

		}
		return array;
	}

}
