package cs230.game.object.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import cs230.game.Level;
import cs230.game.object.cell.Cell;
import cs230.util.Direction;
import cs230.util.GameIds;

/**
 * Class representing a smart targeting enemy.
 * Does almost work apart from the graph representation fed to the A* algorithmn is incorrect.
 * @author Sam Harper - 975431
 * @version 1.0
 * 
 */
public class SmartTargetingEnemy extends Actor {

	/**
	 * Constructor
	 * @param xCoord
	 * @param yCoord
	 * @param level
	 */
	public SmartTargetingEnemy(int xCoord, int yCoord, Level level) {
		super(xCoord, yCoord, GameIds.ENTITIES.ENEMY_SMART);
	}

	/**
	 * Get list of valid enemy walkable nodes.
	 * @param level
	 * @return
	 */
	private ArrayList<Node> computeGrid(Level level) {

		ArrayList<Node> nodes = new ArrayList<>();
		
		Cell[][] cells = level.getCells();

		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[y].length; x++) {
				if (cells[y][x].getId() == GameIds.CELLS.GROUND) {

					Node node = new Node(cells[y][x]);
					nodes.add(node);
				}
			}
		}

		computeNodeLinks(nodes);

		return nodes;
	}

	/**
	 * Work out links between nodes.
	 * @param nodes
	 */
	private void computeNodeLinks(ArrayList<Node> nodes) {

		for(int i = 0; i < nodes.size() - 1; i++) {
			
			Node n1 = nodes.get(i);
			for(int j = i + 1; j < nodes.size(); j++) {
				Node n2 = nodes.get(j);
				
				if(Math.abs(n1.cell.getXCoord() - n2.cell.getXCoord()) == 1) {
					n1.nodeLinks.add(n2);
				}else if(Math.abs(n1.cell.getYCoord() - n2.cell.getYCoord()) == 1) {
					n1.nodeLinks.add(n2);
				}
			}
		}
	}

	/**
	 * Compute enemy move using A*
	 * Move in a random direction if no path can be found.
	 * @param level
	 * @return
	 */
	private Direction computeMove(Level level) {

		Direction dir = Direction.NO_DIRECTION;

		// closed list
		ArrayList<Node> levelGraph = computeGrid(level);

		// open list
		PriorityQueue<Node> openQueue = new PriorityQueue<>((Node n1, Node n2) -> n1.f - n2.f);

		// closed list
		LinkedList<Node> closedList = new LinkedList<>();

		// goalNode
		Player player = level.getPlayer();
		Node playerNode = getNode(levelGraph, level.getCellAt(player.getXCoord(), player.getYCoord()));

		// startNode
		Node startNode = getNode(levelGraph, level.getCellAt(xCoord, yCoord));
		startNode.g = 0;
		startNode.h = calcDistanceToGoal(startNode, playerNode);
		startNode.f = startNode.g + startNode.h;

		openQueue.add(startNode);

		Node endNode = null;
		if (playerNode != null) {

			boolean done = false;
			while (!done && !openQueue.isEmpty()) {

				Node node = openQueue.poll();

				if (node == playerNode) {

					endNode = node;
					done = true;
				}

				for (Node nodeSuccessor : node.nodeLinks) {

					int tempF = (node.g + 1) + calcDistanceToGoal(nodeSuccessor, playerNode);

					if (closedList.contains(nodeSuccessor) && tempF >= nodeSuccessor.f)
						continue;
					else if (!openQueue.contains(nodeSuccessor)) {

						nodeSuccessor.parent = node;
						nodeSuccessor.g = node.g + 1;
						nodeSuccessor.h = calcDistanceToGoal(nodeSuccessor, playerNode);
						nodeSuccessor.f = nodeSuccessor.g + nodeSuccessor.h;

						openQueue.add(nodeSuccessor);
					}
				}
				// put onto closed list
				closedList.add(node);
			}

			System.out.println(startNode.cell.getCoords());
			Node n = endNode;
			while (n.parent != null) {

				System.out.println(n.cell.getCoords());
				n = n.parent;
			}

		} else {

			do {

				dir = getRandomDirection();
			} while (level.getCellAt(xCoord + dir.getXOffset(), yCoord + dir.getYOffset())
					.getId() != GameIds.CELLS.GROUND);
		}

		return dir;
	}

	/**
	 * Get random direction.
	 * @return
	 */
	private Direction getRandomDirection() {

		Random rand = new Random();
		Direction dir = Direction.NO_DIRECTION;

		switch (rand.nextInt(4)) {
		case 0:
			dir = Direction.NORTH;
			break;
		case 1:
			dir = Direction.SOUTH;
			break;
		case 2:
			dir = Direction.EAST;
			break;
		default:
			dir = Direction.WEST;
			break;
		}

		return dir;
	}

	/**
	 * Work out distance to goal for A* (Manhattan Distance)
	 * @param node
	 * @param goal
	 * @return
	 */
	private int calcDistanceToGoal(Node node, Node goal) {

		return Math.abs(node.cell.getXCoord() - goal.cell.getXCoord())
				+ Math.abs(node.cell.getYCoord() - goal.cell.getYCoord());
	}

	/**
	 * Get node
	 * @param levelGraph
	 * @param cell
	 * @return
	 */
	private Node getNode(ArrayList<Node> levelGraph, Cell cell) {

		Node foundNode = null;
		for (Node n : levelGraph) {

			if (n.cell.equals(cell)) {

				foundNode = n;
			}
		}

		return foundNode;
	}

	@Override
	public void update(Level level) {
		Direction dir = computeMove(level);
		xCoord += dir.getXOffset();
		yCoord += dir.getYOffset();
	}

	/**
	 * Node class used for A*.
	 * @author Josh
	 *
	 */
	private class Node {

		private Node parent = null;
		private ArrayList<Node> nodeLinks = new ArrayList<>();
		private Cell cell;
		private int g = Integer.MAX_VALUE; // from start to here
		private int h = Integer.MAX_VALUE; // here to goal
		private int f = Integer.MAX_VALUE; // totalcost

		public Node(Cell cell) {
			this.cell = cell;
		}
	}
}
