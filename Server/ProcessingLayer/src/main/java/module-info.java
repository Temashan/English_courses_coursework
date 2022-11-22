module ProcessingLayer {
    requires java.sql;
    requires Classes;
    exports dbInteration;
    exports serverEndPoint;
    exports serverEndPoint.threads;
}