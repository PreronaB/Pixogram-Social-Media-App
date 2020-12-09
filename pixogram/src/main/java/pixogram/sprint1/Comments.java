package pixogram.sprint1;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="comments")
public class Comments implements List<Comments> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int commentId;
	private String message;
	//ArrayList<String> commentList=new ArrayList<>();
	
	public Comments() {
		
	}

	public Comments(String comment) {
		super();
		this.message = comment;
	}

	public String getComment() {
		return message;
	}

	public void setComment(String comment) {
		this.message = comment;
	}

	@Override
	public String toString() {
		return "Comments [commentId=" + commentId + ", message=" + message + "]";
	}
	

	

}