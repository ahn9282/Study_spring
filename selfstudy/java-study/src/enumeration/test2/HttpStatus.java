package enumeration.test2;

public enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    HttpStatus(int statusCode, String message) {
        this.code = statusCode;
        this.message = message;
    }

    public static HttpStatus findByCode(int httpCodeInput) {
        HttpStatus[] values = HttpStatus.values();
        for (HttpStatus value : values) {
            if (httpCodeInput == value.getStatusCode()) {
                return value;
            }
        }
        return null;
    }

    public boolean isSuccess(){
        return code >= 200 && code <= 299;
    }

    public int getStatusCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
