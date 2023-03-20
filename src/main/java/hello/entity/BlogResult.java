package hello.entity;

public class BlogResult extends Result<Blog> {

    public BlogResult(String status, String msg, Blog data) {
        super(status, msg, data);
    }

    public  static BlogResult okBlogWithUser(String status, String msg, Blog data) {
        return new BlogResult(status, msg, data);
    }

    public static BlogResult failBlogResult(String msg) {
        return new BlogResult("fail", msg, null);
    }
}
