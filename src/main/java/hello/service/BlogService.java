package hello.service;

import hello.entity.BlogResult;
import hello.mapper.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogDao blogDao;

    @Autowired
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public BlogResult getBlogs(int page, int pageNum, Integer userId) {
        try {
            int total = blogDao.countBlog();
            int totalPage = total % pageNum == 0 ? total / pageNum : total / pageNum + 1;
            return BlogResult.okBlogResult(blogDao.getBlogs(page, pageNum, userId), page, total, totalPage);
        } catch (Exception e){
            return BlogResult.failBlogResult("系统异常");
        }
    }
}
