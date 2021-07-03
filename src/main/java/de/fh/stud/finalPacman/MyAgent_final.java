package de.fh.stud.finalPacman;

import de.fh.kiServer.agents.Agent;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.search.searchTypes.AStarSearch;
import de.fh.stud.finalPacman.search.searchTypes.GhostSearch;

public class MyAgent_final extends PacmanAgent_2021 {

	public MyAgent_final(String name) {
		super(name);
	}
	Search search = null;
	
	public static void main(String[] args) {
		MyAgent_final agent = new MyAgent_final("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	/**
	 * @param percept - Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
	 * @param actionEffect - Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
	 */
	@Override
	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {

		if(search == null)
			search = new GhostSearch(percept.getView(), new Pacman(percept));

		try {
			search.runRoundChecks(percept);
			Coordinates nextDot = search.find(PacmanTileType.DOT);
			search.calculateNextSteps(nextDot);
			return search.getNextMove();
		} catch (NotFoundException ignored) { }

		return PacmanAction.QUIT_GAME;
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
