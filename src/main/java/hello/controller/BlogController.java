package hello.controller;

import hello.entity.Blog;
import hello.entity.BlogListResult;
import hello.entity.BlogResult;
import hello.entity.User;
import hello.service.BlogService;
import hello.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@RestController
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;

    @Inject
    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping("/blog")
    public BlogListResult getBlogs(@RequestParam int page,
                                   @RequestParam(defaultValue = "10") int pageNum,
                                   @RequestParam(required = false) Integer userId) {
        return blogService.getBlogs(page, pageNum, userId);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult getBlog(@PathVariable("blogId") int blogId) {
        return BlogResult.okBlogWithUser("ok", "获取成功", blogService.getBlogById(blogId)) ;
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogResult newBlog(@RequestBody Map<String, String> param) {
        if(getCurrentUser().isPresent()) {
            return BlogResult.okBlogWithUser("ok", "获取成功", blogService.insertBlog(fromParam(param, getCurrentUser().get()))) ;
        } else {
            return BlogResult.failBlogResult("登录后才能操作");
        }
//        try {
//            return getCurrentUser()
//                    .map(user -> blogService.insertBlog(fromParam(param, user)))
//                    .orElse(BlogResult.failBlogResult("登录后才能操作"));
//        } catch (IllegalArgumentException e) {
//            return BlogResult.failBlogResult(e.getMessage());
//        }
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public BlogListResult deleteBlog(@PathVariable("blogId") int blogId) {
        if(getCurrentUser().isPresent()) {
            return blogService.deleteBlog(blogId, getCurrentUser().get());
        } else {
            return BlogListResult.failBlogResult("登录后才能操作");
        }
//        try {
//            getCurrentUser().isPresent()
//            return getCurrentUser()
//                    .map(user -> blogService.deleteBlog(blogId, user))
//                    .orElse(BlogResult.failBlogResult("登录后才能操作"));
//        } catch (IllegalArgumentException e) {
//            return BlogResult.failBlogResult(e.getMessage());
//        }
    }

    private Blog fromParam(Map<String, String> params, User user) {
        Blog blog = new Blog();
        String title = params.get("title");
        String content = params.get("content");
        String description = params.get("description");

//        AssertUtils.assertTrue(StringUtils.isNotBlank(title) && title.length() < 100, "title is invalid!");
//        AssertUtils.assertTrue(StringUtils.isNotBlank(content) && content.length() < 10000, "content is invalid");

//        if (StringUtils.isBlank(description)) {
//            description = content.substring(0, Math.min(content.length(), 10)) + "...";
//        }

        blog.setTitle(title);
        blog.setContent(content);
        blog.setDesc(description);
        blog.setUser(user);
        return blog;
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(userService.getUserByUsername(authentication == null ? null : authentication.getName()));
    }
}
