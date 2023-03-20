package hello.entity;

import java.util.List;

public class BlogListResult extends Result<List<Blog>> {
    int total;
    int page;
    int totalPage;

    public BlogListResult(String status, String msg, List<Blog> data, int total, int page, int totalPage) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogListResult okBlogResult(List<Blog> data, int page, int total, int totalPage) {
        return new BlogListResult("ok", "获取成功", data, total, page, totalPage);
    }

    public static BlogListResult failBlogResult(String msg) {
        return new BlogListResult("fail", msg, null, 0, 0,0);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
