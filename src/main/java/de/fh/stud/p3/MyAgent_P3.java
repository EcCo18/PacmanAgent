package de.fh.stud.p3;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;

public class MyAgent_P3 extends PacmanAgent_2021 {

	private PacmanAction nextAction;
	private Node loesungsNode;

	public MyAgent_P3(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	/**
	 * @param percept - Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
	 * @param actionEffect - Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
	 */
	@Override
	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {

		//Gebe den aktuellen Zustand der Welt auf der Konsole aus
		Util.printView(percept.getView());
		
		//Wenn noch keine Lösung gefunden wurde, dann starte die Suche
		if (loesungsNode == null) {

			Suche deep = new Suche(new Node(percept.getView(), new Coordinates(percept.getPosX(), percept.getPosY())), "deep");
			Suche width = new Suche(new Node(percept.getView(), new Coordinates(percept.getPosX(), percept.getPosY())), "width");
			Suche greedy = new Suche(new Node(percept.getView(), new Coordinates(percept.getPosX(), percept.getPosY())), "greedy");
			Suche ucs = new Suche(new Node(percept.getView(), new Coordinates(percept.getPosX(), percept.getPosY())), "ucs");
			Suche aStar = new Suche(new Node(percept.getView(), new Coordinates(percept.getPosX(), percept.getPosY())), "as");

			try
			{
				loesungsNode = aStar.start();
				loesungsNode.getWalkedPath().remove(0);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			//Effiziente Abarbeitung der Tabellde

			/*try
			{
				System.out.println("Deep ---");
				loesungsNode = deep.start();
				//System.out.println("Width ---");
				loesungsNode = width.start();
				System.out.println("Greedy ---");
				loesungsNode = greedy.start();
				//System.out.println("UCS ---");
				loesungsNode = ucs.start();
				System.out.println("A-Stern ---");
				loesungsNode = aStar.start();
			} catch (Exception e)
			{
				e.printStackTrace();
			}*/
		}
		
		//Wenn die Suche eine Lösung gefunden hat, dann ermittle die als nächstes auszuführende Aktion
		if (loesungsNode != null) {

			Node nextNode = loesungsNode.getWalkedPath().get(0);
			loesungsNode.getWalkedPath().remove(0);

			Coordinates currentCoordinates = new Coordinates(percept.getPosX(), percept.getPosY());
			Coordinates desiredCoordinates =nextNode.getPacPos();

			nextAction = coordinatesToAction(currentCoordinates, desiredCoordinates);

		} else {
			//Ansonsten wurde keine Lösung gefunden und der Pacman kann das Spiel aufgeben
			nextAction = PacmanAction.QUIT_GAME;
		}
		
		return nextAction;
	}

	private PacmanAction coordinatesToAction (Coordinates nowCoords, Coordinates goalCoords) {

		PacmanAction returnAction;

		switch (nowCoords.getPosX() - goalCoords.getPosX()) {

			case 1 : returnAction = PacmanAction.GO_WEST; break;
			case -1: returnAction = PacmanAction.GO_EAST; break;
			default:
				switch (nowCoords.getPosY() - goalCoords.getPosY()) {

					case 1 : returnAction = PacmanAction.GO_NORTH; break;
					case -1: returnAction = PacmanAction.GO_SOUTH; break;
					default: returnAction = PacmanAction.QUIT_GAME;
				}
		}



		return returnAction;
	}

	@Override
	protected void onGameStart(PacmanStartInfo startInfo) {
		System.out.println("Game started!");
	}

	@Override
	protected void onGameover(PacmanGameResult gameResult) {
		System.out.println("Game over!");
	}
	
}
