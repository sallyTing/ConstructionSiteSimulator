package org.example.constructionsitesimulator.exceptions;

import org.example.constructionsitesimulator.models.ConstructionSite;

public class ProtectedTreePenaltyException extends Exception{
    private final ConstructionSite site;

    public ProtectedTreePenaltyException(final ConstructionSite site) {
        this.site = site;
    }

    public ConstructionSite getSite() {
        return site;
    }
}
