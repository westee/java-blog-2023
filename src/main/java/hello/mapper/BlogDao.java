package hello.mapper;

import hello.entity.Blog;
import hello.entity.BlogListResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BlogDao {
    SqlSession session;

    @Autowired
    public BlogDao(SqlSession session) {
        this.session = session;
    }

    public List<Blog> getBlogs(int page, int pageNum, Integer userId) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("pageLimit", pageNum);
        params.put("userId", userId);
        params.put("pageOffset", (page - 1) * pageNum);
        return session.selectList("selectBlogs", params);
    }

    public int countBlog(Integer userId){
        return session.selectOne("countBlog", userId);
    }

    public Blog getBlogById(int blogId) {
        return session.selectOne("getBlogById", blogId);

    }

    public Blog insertBlog(Blog blog) {
        session.insert("insertBlog", blog);
        return getBlogById(blog.getId());
    }

    public BlogListResult deleteBlog(int blogId) {
        return session.selectOne("deleteBlog", blogId);
    }
}
