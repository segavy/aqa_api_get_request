package by.intexsoft.api;

//import lombok.Data;

//@Data
public enum ResponseCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
