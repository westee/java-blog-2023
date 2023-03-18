package hello.entity;


public class Result<T> {
    String status;
    String msg;
    boolean isLogin;
    T data;

    public Result(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Result(String status, String msg, boolean isLogin, T data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) { isLogin = login; }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
