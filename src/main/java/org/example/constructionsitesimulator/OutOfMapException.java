package org.example.constructionsitesimulator;

public class OutOfMapException extends Exception {
    private final ConstructionSite site;

    public OutOfMapException(final ConstructionSite site) {
        this.site = site;
    }

    public ConstructionSite getSite() {
        return site;
    }
}
