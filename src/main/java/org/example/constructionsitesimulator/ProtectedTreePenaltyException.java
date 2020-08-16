package org.example.constructionsitesimulator;

public class ProtectedTreePenaltyException extends Exception{
    private final ConstructionSite site;

    public ProtectedTreePenaltyException(final ConstructionSite site) {
        this.site = site;
    }

    public ConstructionSite getSite() {
        return site;
    }
}
