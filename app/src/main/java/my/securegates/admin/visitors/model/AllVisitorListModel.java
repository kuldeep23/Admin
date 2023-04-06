package my.securegates.admin.visitors.model;

public class AllVisitorListModel {

    String visitor_id, visitor_name, visitor_type,visitor_image, visitor_mobile,visitor_status, visitor_approve_by, visitor_enter_time, visitor_enter_date;

    public AllVisitorListModel() {
    }

    public AllVisitorListModel(String visitor_id, String visitor_name, String visitor_type, String visitor_image, String visitor_mobile, String visitor_status, String visitor_approve_by, String visitor_enter_time, String visitor_enter_date) {
        this.visitor_id = visitor_id;
        this.visitor_name = visitor_name;
        this.visitor_type = visitor_type;
        this.visitor_image = visitor_image;
        this.visitor_mobile = visitor_mobile;
        this.visitor_status = visitor_status;
        this.visitor_approve_by = visitor_approve_by;
        this.visitor_enter_time = visitor_enter_time;
        this.visitor_enter_date = visitor_enter_date;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getVisitor_type() {
        return visitor_type;
    }

    public void setVisitor_type(String visitor_type) {
        this.visitor_type = visitor_type;
    }

    public String getVisitor_image() {
        return visitor_image;
    }

    public void setVisitor_image(String visitor_image) {
        this.visitor_image = visitor_image;
    }

    public String getVisitor_mobile() {
        return visitor_mobile;
    }

    public void setVisitor_mobile(String visitor_mobile) {
        this.visitor_mobile = visitor_mobile;
    }

    public String getVisitor_status() {
        return visitor_status;
    }

    public void setVisitor_status(String visitor_status) {
        this.visitor_status = visitor_status;
    }

    public String getVisitor_approve_by() {
        return visitor_approve_by;
    }

    public void setVisitor_approve_by(String visitor_approve_by) {
        this.visitor_approve_by = visitor_approve_by;
    }

    public String getVisitor_enter_time() {
        return visitor_enter_time;
    }

    public void setVisitor_enter_time(String visitor_enter_time) {
        this.visitor_enter_time = visitor_enter_time;
    }

    public String getVisitor_enter_date() {
        return visitor_enter_date;
    }

    public void setVisitor_enter_date(String visitor_enter_date) {
        this.visitor_enter_date = visitor_enter_date;
    }
}