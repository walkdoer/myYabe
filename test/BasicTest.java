import models.*;
import org.codehaus.groovy.antlr.UnicodeEscapingReader;
import org.junit.*;

import play.test.*;

import java.util.List;
import java.util.Map;

public class BasicTest extends UnitTest {

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = User.find("byEmail", "bob@gmail.com").first();
        System.out.println("test");
        // Test
        assertNotNull(bob);
        assertEquals("Bob", bob.fullname);
    }

    @Before
    public void setup() {
        System.out.println("setup delete database");
        Fixtures.deleteDatabase();
    }
    @Test
    public void createPost() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        new Post(bob, "My first post", "Hello world").save();

        // Test that the post has been created
        assertEquals(1, Post.count());

        // Retrieve all posts created by Bob
        List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

        // Tests
        assertEquals(1, bobPosts.size());
        Post firstPost = bobPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(bob, firstPost.author);
        assertEquals("My first post", firstPost.title);
        assertEquals("Hello world", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }
    @Test
    public void postCommmet() {
        // Create a new user and save it
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        Post bobPost = new Post(bob, "My first post", "Hello world").save();

        // Post a first comment
        bobPost.addComment("Jeff", "Nice post");
        bobPost.addComment("Tom", "I knew that !");

        // Count things
        assertEquals(1, User.count());
        assertEquals(1, Post.count());
        assertEquals(2, Comment.count());

        // Retrieve Bob's post
        bobPost = Post.find("byAuthor", bob).first();
        assertNotNull(bobPost);

        // Navigate to comments
        assertEquals(2, bobPost.comments.size());
        assertEquals("Jeff", bobPost.comments.get(0).author);

        // Delete the post
        bobPost.delete();

        // Check that all comments have been deleted
        assertEquals(1, User.count());
        assertEquals(0, Post.count());
        assertEquals(0, Comment.count());

    }

    @Test
    public void fullTest() {

        //Fixtures.loadModels("data.yml");

        // Count things
        assertEquals(2, User.count());
        assertEquals(3, Post.count());
        assertEquals(3, Comment.count());

        // Try to connect as users
        //assertNotNull(User.connect("bob@gmail.com", "secret"));
        //assertNotNull(User.connect("jeff@gmail.com", "secret"));
        //assertNull(User.connect("jeff@gmail.com", "badpassword"));
        //assertNull(User.connect("tom@gmail.com", "secret"));

        // Find all of Bob's posts
        List<Post> bobPosts = Post.find("author.email", "bob@gmail.com").fetch();
        assertEquals(2, bobPosts.size());

        // Find all comments related to Bob's posts
        List<Comment> bobComments = Comment.find("post.author.email", "bob@gmail.com").fetch();
        assertEquals(3, bobComments.size());

        // Find the most recent post
        Post frontPost = Post.find("order by postedAt desc").first();
        assertNotNull(frontPost);
        assertEquals("About the model layer", frontPost.title);

        // Check that this post has two comments
        assertEquals(2, frontPost.comments.size());

        // Post a new comment
        frontPost.addComment("Jim", "Hello guys");
        assertEquals(3, frontPost.comments.size());
        assertEquals(4, Comment.count());
    }
    @Test
    public void testTags() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        Post bobPost = new Post(bob, "My first post", "Hello world").save();
        Post anotherBobPost = new Post(bob, "Hop", "Hello world").save();

        // Well
        assertEquals(0, Post.findTaggedWith("Red").size());

        // Tag it now
        bobPost.tagItWith("Red").tagItWith("Blue").tagItWith("gray").save();
        anotherBobPost.tagItWith("Red").tagItWith("Green").save();

        // Check
        assertEquals(2, Post.findTaggedWith("Red").size());
        assertEquals(1, Post.findTaggedWith("Blue").size());
        assertEquals(1, Post.findTaggedWith("gray").size());
        assertEquals(1, Post.findTaggedWith("Green").size());

        assertEquals(1, Post.findTaggedWith("Red", "Blue", "gray").size());
        assertEquals(1, Post.findTaggedWith("Red", "Green").size());
        assertEquals(0, Post.findTaggedWith("Red", "Green", "Blue").size());
        assertEquals(0, Post.findTaggedWith("Green", "Blue").size());

        List<Map> cloud = Tag.getCloud();
        System.out.printf(cloud.toString());
        assertEquals(
                "[{tag=Blue, pound=1}, {tag=Green, pound=1}, {tag=Red, pound=2}, {tag=gray, pound=1}]",
                cloud.toString()
        );
    }
}
