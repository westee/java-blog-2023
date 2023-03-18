package hello.entity;

public class LoginResult extends Result<User> {
    public LoginResult(String status, String msg, boolean isLogin) {
        super(status, msg, isLogin);
    }

    public LoginResult(String status, String msg, boolean isLogin, User data) {
        super(status, msg, isLogin, data);
    }

    public static LoginResult fail(String msg) {
        return new LoginResult("fail", msg, false);
    }

    public static LoginResult success(String msg, User data, boolean isLogin) {
        return new LoginResult("success", msg, isLogin, data);
    }

    public static LoginResult success(String status, String msg, boolean isLogin) {
        return new LoginResult(status, msg, isLogin);
    }

}
