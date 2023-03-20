package hello.service;

import hello.entity.*;
import hello.mapper.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogDao blogDao;
    private final UserService userService;

    @Autowired
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogListResult getBlogs(int page, int pageNum, Integer userId) {
        try {
            int total = blogDao.countBlog(userId);
            int totalPage = total % pageNum == 0 ? total / pageNum : total / pageNum + 1;
            List<Blog> blogs = blogDao.getBlogs(page, pageNum, userId);
            blogs.forEach(blog -> blog.setUser(userService.getUserById(blog.getUserId())));
            return BlogListResult.okBlogResult(blogs, page, total, totalPage);
        } catch (Exception e) {
            return BlogListResult.failBlogResult("系统异常");
        }
    }

    public BlogListResult deleteBlog(int blogId, User user) {
        return blogDao.deleteBlog(blogId);
    }

    public Blog insertBlog(Blog fromParam) {
        int id = blogDao.insertBlog(fromParam);
        return blogDao.getBlogById(id);
//        BlogResult.okBlogResult();
    }

    public Blog getBlogById(int blogId) {
        Blog blog = blogDao.getBlogById(blogId);
        blog.setUser(userService.getUserById(blog.getUserId()));
        return blog;
    }
}
