package my.securegates.admin.visitors.model;

public class Model {
    String visitortype, visitorname, visitormobile, visitorflat, visitorimage;

    public Model() {
    }

    public Model(String visitortype, String visitorname, String visitormobile, String visitorflat, String visitorimage) {
        this.visitortype = visitortype;
        this.visitorname = visitorname;
        this.visitormobile = visitormobile;
        this.visitorflat = visitorflat;
        this.visitorimage = visitorimage;
    }

    public String getVisitortype() {
        return visitortype;
    }

    public void setVisitortype(String visitortype) {
        this.visitortype = visitortype;
    }

    public String getVisitorname() {
        return visitorname;
    }

    public void setVisitorname(String visitorname) {
        this.visitorname = visitorname;
    }

    public String getVisitormobile() {
        return visitormobile;
    }

    public void setVisitormobile(String visitormobile) {
        this.visitormobile = visitormobile;
    }

    public String getVisitorflat() {
        return visitorflat;
    }

    public void setVisitorflat(String visitorflat) {
        this.visitorflat = visitorflat;
    }

    public String getVisitorimage() {
        return visitorimage;
    }

    public void setVisitorimage(String visitorimage) {
        this.visitorimage = visitorimage;
    }
}
