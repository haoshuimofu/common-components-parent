package threadPool;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Main {


    static class BindLinkInfo {
        // ==预处理 start===
        private AOI aoi;
        private AOI virtualAoi;
        // ==预处理 end===


        private AoiEntrance aoiEntranceResult;
        private List<Pair<Point, BindLink>> pointMultiMatchResults;
        private BindLink nearestResult;
    }

    static class AoiEntrance {
        private AOI aoi;
        private List<Pair<Point, EndTrack>> endTracks;


    }

    /**
     * 末端估计
     */
    static class EndTrack {
        private List<Point> points;
        private double duration;
        private double length;
    }

    static class BindLink {
        private String edgeId;
        private Point projection;
        private Point startPoint;
        private int startLevel;
        private Point endPoint;
        private int endLevel;
        private EndTrack projectionToStart;
        private EndTrack projectionToEnd;
    }

}
