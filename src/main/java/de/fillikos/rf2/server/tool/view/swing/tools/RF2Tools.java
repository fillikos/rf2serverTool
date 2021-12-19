package de.fillikos.rf2.server.tool.view.swing.tools;

import de.fillikos.rf2.service.webui.httpss.model.PitState;
import de.fillikos.rf2.service.webui.httpss.model.User;

public class RF2Tools {
    public static String long2LapTime(String lapTime) {
        if(lapTime.equals("-1.0")) {
            return "0.00.000";
        }
        float time = Float.parseFloat(lapTime);
        long ms = (long) (time * 1000);
        return String.format("%d.%02d.%03d", ((ms / 1000) / 60), ((ms/1000) % 60), (ms % 1000));
    }

    public static String[] user2TableView(User u) {



        //Erstellen einer Zeile für das Livetiming -> in der TESTDAY SESSION !!!
//      String[] columns = new String[]{"Pos", "Team", "Fahrer", "Laps", "Fastest Lap", "Last Lap", "S1", "S2", "S3", "PIT"};
        //TODO wenn BestLapTime = -1.0
        String bestLapTime = long2LapTime(u.getBestLapTime());
        //TODO wenn LastLapTime = -1.0
        String lastLapTime = long2LapTime(u.getLastLapTime());
        if(u.getPitStateEnum() == PitState.EXITING || u.getLastLapTime().equals("-1.0")) {
            lastLapTime = "";
        }

        String sector1 = "";
        String sector2 = "";
        String sector3 = "";
        if(u.getLastLapTime().equals("-1.0") && u.getCurrentSectorTime1().equals("-1.0")) {
            sector1 = float2SectorTime(Float.parseFloat(u.getBestSectorTime1()));
            if(!u.getBestSectorTime2().equals("-1.0")) {
                sector2 = float2SectorTime(Float.parseFloat(u.getBestSectorTime2()) - Float.parseFloat(u.getBestSectorTime1()));
            } else {
                sector2 = "00.000";
            }
            sector3 = float2SectorTime(Float.parseFloat(u.getBestLapTime()) - Float.parseFloat(u.getBestSectorTime2()));
        } else if ( (!u.getLastLapTime().equals("-1.0")) && u.getCurrentSectorTime1().equals("-1.0")) {
            sector1 = float2SectorTime(Float.parseFloat(u.getLastSectorTime1()));
            if(!u.getLastSectorTime2().equals("-1.0")) {
                sector2 = float2SectorTime(Float.parseFloat(u.getLastSectorTime2()) - Float.parseFloat(u.getLastSectorTime1()));
            } else {
                sector2 = "00.000";
            }
            sector3 = float2SectorTime(Float.parseFloat(u.getLastLapTime()) - Float.parseFloat(u.getLastSectorTime2()));
        } else {
            sector1 = float2SectorTime(Float.parseFloat(u.getCurrentSectorTime1()));
            if(!u.getCurrentSectorTime2().equals("-1.0")) {
                sector2 = float2SectorTime(Float.parseFloat(u.getCurrentSectorTime2()) - Float.parseFloat(u.getCurrentSectorTime1()));
            } else {
                sector2 = "";
            }
            sector3 = "";
        }

        String pitState = "none";
        switch (u.getPitStateEnum()) {
            case NONE:
            case REQUEST: pitState = "OUT"; break;
            case EXITING:
            case PITTING:
            case STOPPED:
            case ENTERING:
            case INPITLANE: pitState = "IN"; break;
        }
        //TODO verhalten in den verschiedenen Sessions... bisher auf TESTDAY
        String colSector1 = "000000";
        if(u.getLastSectorTime1().equals(u.getBestSectorTime1())) {
            colSector1 = "ff00ff";
        }
        String colSector2 = "000000";
        if(u.getLastSectorTime2().equals(u.getBestSectorTime2())) {
            colSector2 = "ff00ff";
        }
        return new String[]{
                String.format("%02d", Integer.parseInt(u.getPosition())),
                String.format("<html><body>%s<br><p style=\"color:red\">%s</p></body></html>", u.getFullTeamName(), u.getCarClass()),
                u.getDriverName(),
                u.getLapsCompleted(),
                bestLapTime,
                lastLapTime,
                String.format("<html><body><p style=\"color:#%s\">%s</p>" +
                                "<p style=\"color:#aaaaaa\">%s</p></body></html>",
                        colSector1, sector1, float2SectorTime(Float.parseFloat(u.getBestSectorTime1()))),
                String.format("<html><body><p style=\"color:#%s\">%s</p>" +
                                "<p style=\"color:#aaaaaa\">%s</p></body></html>",
                        colSector2, sector2, float2SectorTime(Float.parseFloat(u.getBestSectorTime2()) - Float.parseFloat(u.getBestSectorTime1()))),
                String.format("<html><body><p style=\"color:#%s\">%s</p>" +
                                "<p style=\"color:#aaaaaa\">%s</p></body></html>",
                        "000000", sector3, float2SectorTime(Float.parseFloat(u.getBestLapTime()) - Float.parseFloat(u.getBestSectorTime2()))),
                sector3,
                pitState
        };
    }

    private static String float2SectorTime(float sectorTime) {
        if(sectorTime == -1.0) {
            return "00.000";
        }
        long ms = (long) (sectorTime * 1000);
        if(((ms / 1000) / 60) > 0) {
            return String.format("%d.%02d.%03d", ((ms / 1000) / 60), ((ms/1000) % 60), (ms % 1000));
        }
        return String.format("%02d.%03d", ((ms/1000) % 60), (ms % 1000));
    }
}
