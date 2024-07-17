package CRM.project.dto;

public enum Availability {

    ONLINE("1"),
    AWAY("2"),
    OFFLINE("3")
    ;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Availability(String code) {
        this.code = code;
    }

    public static Availability fromCode(String code) {
        for (Availability availability : Availability.values()) {
            if (availability.getCode().equals(code)) {
                return availability;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }

}
