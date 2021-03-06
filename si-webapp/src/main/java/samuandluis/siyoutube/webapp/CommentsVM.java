package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.Comment;
import samuandluis.siyoutube.persistence.Comments;
import samuandluis.siyoutube.persistence.Video;
import samuandluis.siyoutube.persistence.Videos;
import samuandluis.siyoutube.persistence.User;
import samuandluis.siyoutube.persistence.Users;

public class CommentsVM {
	
	private EntityManager em;
	private Comments comments;
	private Videos videos;
	private Users users;
	private Video video;
	private User user;
	
	private boolean isEditing = false;
	
	// Comment under edition...
	private Comment currentComment;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.comments = new Comments(em);
		this.videos = new Videos(em);
		this.users = new Users(em);
	}
	
	public List<Comment> getComments() {
		return this.comments.findAll();
	}
	
	public List<Video> getVideos() {
		return this.videos.findAll();
	}
	
	public List<User> getUsers() {
		return this.users.findAll();
	}
	
	public Comment getCurrentComment() {
		return currentComment;
	}
	
	public void setCurrentComment(Comment currentComment) {
		this.currentComment = currentComment;
	}
	
	@Command
	@NotifyChange("currentComment")
	public void newComment() {
		this.isEditing = false;
		this.currentComment = new Comment();
	}
	
	@Command
	@NotifyChange("currentComment")
	public void resetEditing() {
		this.currentComment = null;
	}
	
	@Command
	@NotifyChange({"currentComment", "comments"})
	public void saveComment() {
		
		this.em.getTransaction().begin();
			if (!isEditing) {
				this.comments.addNewComment(this.currentComment);
			}
		this.em.getTransaction().commit();
		
		this.currentComment = null;
	}
	
	@Command
	@NotifyChange("comments")
	public void delete(@BindingParam("c") Comment Comment) {
		this.em.getTransaction().begin();
			this.comments.deleteComment(Comment);
		this.em.getTransaction().commit();
	}
	
	@Command
	@NotifyChange("currentComment")
	public void edit(@BindingParam("c") Comment Comment) {
		this.isEditing = true;
		this.currentComment = Comment;
	}
}
