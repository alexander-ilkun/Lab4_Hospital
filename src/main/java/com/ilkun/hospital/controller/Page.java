package com.ilkun.hospital.controller;

/**
 * This class provides convenient maintence for page forwarding or redirecting.
 *
 * @author alexander-ilkun
 */
public class Page {
    
    private String page;
    private boolean redirect;
    
    public Page(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
    }
    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean getRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

}
