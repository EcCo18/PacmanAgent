package de.fh.stud.finalPacman.deadEnd;

public class DeadEndReport {

    private final int deadEndLength;

    private final DeadEndType isDeadEnd;

    public DeadEndReport(int deadEndLength, DeadEndType isDeadEnd) {

        this.deadEndLength = deadEndLength;
        this.isDeadEnd = isDeadEnd;
    }

    public boolean isDeadEnd() {

        return isDeadEnd == DeadEndType.DEAD_END;
    }

    public int getDeadEndLength() {

        return deadEndLength;
    }
}
