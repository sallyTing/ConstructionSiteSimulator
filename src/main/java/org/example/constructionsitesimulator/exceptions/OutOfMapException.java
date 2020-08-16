package org.example.constructionsitesimulator.exceptions;

import org.example.constructionsitesimulator.models.ConstructionSite;

public class OutOfMapException extends Exception {
    private final ConstructionSite site;

    public OutOfMapException(final ConstructionSite site) {
        this.site = site;
    }

    public ConstructionSite getSite() {
        return site;
    }
}
