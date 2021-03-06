package samuandluis.siyoutube.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Channel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private User user;
	
	//Con esto, si se eliminar un canal se eliminan sus videos
	@OneToMany(mappedBy="channel", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<Video> listVideos = new ArrayList<Video>();
	
	private String description;
	
	public Channel () {
		this.listVideos = new ArrayList<Video>();
	}
	
	public int getId() {
		return id;
	}
	
	public List<Video> getListVideos() {
		return listVideos;
	}
	
	public void setListVideos(List<Video> listVideos) {
		this.listVideos = listVideos;
	}	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser (User u) {
		this.user = u;
	}
	
	void internalAddVideo (Video v) {
		this.listVideos.add(v);
	}
	
	void internalRemoveVideo (Video v) {
		this.listVideos.remove(v);
	}
	
	
}
