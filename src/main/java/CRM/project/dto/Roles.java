package CRM.project.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Roles {
    USER("1"),
    MANAGER("2"),
    ADMIN("3")
    ;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Roles(String code) {
        this.code = code;
    }

    public static Roles fromCode(String code) {
        for (Roles role : Roles.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
