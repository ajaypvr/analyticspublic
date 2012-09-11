package com.mapinfopage.server.facebook;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;

public class Facebook {
	
	private static final String FACEBOOK_APP_ID = "356405047717538";
	private static final String FACEBOOK_APP_SECRET = "1fbb237c011991c71dabafa109499847";
	
	private static final String AJ_ACCESS_TOKEN = "AAAFEJgRD7qIBAPCRQ0ZAnnR9zfrIUCBd9pLW4wYklA9HsMj9goq9AWLXiuAZA03zMx0cCkzaTO7kXtxz7gKH6rc8BJPbTXCRoEiJRdgAZDZD";
	
	private static final String SOFIA_ACCESS_TOKEN = "AAAFEJgRD7qIBAGsc6Cxq4OaZAEF9pvot05qfEOZBlY7NbjCeooRXpZBaZCWAeUWH9392UURuZBZCdhJlsjZCdUmfm8p6ZBF1Vy4WOjkQjKTYbgZDZD";
	/**
	 * @param args
	 */
	public static void main(String[] args){
		FacebookClient facebookClient = new DefaultFacebookClient(SOFIA_ACCESS_TOKEN);
		
		/*User meUser = facebookClient.fetchObject("me", User.class);
		Page page = facebookClient.fetchObject("cocacola", Page.class);

		System.out.println("User name: " + meUser);
		System.out.println("Page likes: " + page);
		
		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
		*/
		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
		
		Connection<Post> myHome = facebookClient.fetchConnection("me/home", Post.class);
		Connection<Post> myPosts = facebookClient.fetchConnection("me/posts", Post.class);

		System.out.println("Count of my friends: " + myFeed.getData().size());
		//System.out.println("First item in my feed: " + myFeed.getData().get(0));

		// Connections support paging and are iterable
		int i = 0;
		for (List<Post> myFeedConnectionPage : myFeed){
		  for (Post post : myFeedConnectionPage){
			  if(++i%25 == 0){
				  System.out.println("25 limit reached");
			  }
			  String message = post.getMessage();
			  if(message != null){
				  System.out.println("Time : " + post.getCreatedTime() + ",  Post Message : " + message);
			  }
			  
			  List<Comment> commentList = post.getComments().getData();
			  for(Comment comment : commentList){
				  String commentMessage = comment.getMessage();
				  if(commentMessage != null){
					System.out.println("	Time : " + comment.getCreatedTime() + ", Comment Message : "+ commentMessage);  
				  }
			  }
			  
		  }
		}
		
		/*
		Connection<Post> publicSearch = facebookClient.fetchConnection("search", Post.class,
				    Parameter.with("q", "jewelry"), Parameter.with("type", "post"));
		
		System.out.println("Public search: " + publicSearch.getData().size());
		System.out.println("Public search: " + publicSearch.getData().get(0));*/
		
		Date twoWeekAgo = new Date((new Date()).getTime()  - (60L * 60L * 24L * 13L * 1000L));
		
		long twoWeekAgoInSeconds= twoWeekAgo.getTime()/1000l;//1262196000l; //
		//System.out.println("twoWeekAgo = " + new Date(1262196000000l));
		
		/*Connection<StatusMessage> targetedSearch = facebookClient.fetchConnection("me/home", StatusMessage.class,
				    Parameter.with("q", "Toyota"),  Parameter.with("since", twoWeekAgo));
		
		
		System.out.println("Posts for jewelry: " + targetedSearch.getData().size());
		if(targetedSearch.getData().size() > 0){
			System.out.println("Posts for jewelry: " + targetedSearch.getData().get(0).getMessage());
		}*/
		
		//JASON OBJECT
		
		String query = "SELECT filter_key, created_time, attachment,post_id, actor_id, target_id, message, type, comments FROM stream WHERE   source_id = me()";//and created_time < " + twoWeekAgoInSeconds;
		//String query = "SELECT source_id, created_time, attachment,post_id, actor_id, target_id, message, type, comments, filter_key FROM stream WHERE   filter_key in (SELECT filter_key FROM stream_filter WHERE uid=me())";//and created_time < " + twoWeekAgoInSeconds;
		//String query = "SELECT filter_key, source_id, created_time, attachment,post_id, actor_id, target_id, message, type, comments FROM stream WHERE   filter_key in ('others')";// limit 50";//and created_time < " + twoWeekAgoInSeconds;
		
		/*List<JsonObject> queryResults = facebookClient.executeQuery(query, JsonObject.class);
		
		int i = 0;
		for(JsonObject object : queryResults){
			
			long timeInMilliseconds = object.getLong("created_time") * 1000l;
			
			Date postCreationsDate = new Date(timeInMilliseconds);
			
			System.out.println( postCreationsDate+ " message = " + object.getString("message")
					//+ " " + new Date( new Long(object.getString("created_time")) * 1000l )+ " " + object.getString("attachment")
					+ " filter_key = " + object.getString("filter_key") 
					//+ " comments = " + object.getString("comments") + " type = " + object.getString("type")
					);
		}*/
		
		/*query = "SELECT type, filter_key FROM stream_filter WHERE uid=me()";//and created_time < " + twoWeekAgoInSeconds;
		queryResults = facebookClient.executeQuery(query, JsonObject.class);
		
		 i = 0;
		for(JsonObject object : queryResults){
			System.out.println(" message = " + i++ + " " + object.getString("filter_key") + " "+ object.getString("filter_key"));
		}*/
		
		
	}
	
	

}
