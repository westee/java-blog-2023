package hello.entity;

public class LoginResult extends Result<User> {
    boolean isLogin;

    public LoginResult(String status, String msg, boolean isLogin) {
        super(status, msg);
        this.isLogin = isLogin;
    }

    public LoginResult(String status, String msg, boolean isLogin, User data) {
        super(status, msg, data);
        this.isLogin = isLogin;
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

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
