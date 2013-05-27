package com.vacationanalytics.tweets;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import twitter4j.Annotations;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class MockStatus implements Status{

	@Override
	public int compareTo(Status arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RateLimitStatus getRateLimitStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAccessLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getCreatedAt() {
		return new Date();
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTruncated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getInReplyToStatusId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getInReplyToUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getInReplyToScreenName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeoLocation getGeoLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFavorited() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser() {
		return new User(){

			@Override
			public int compareTo(User o) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public RateLimitStatus getRateLimitStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getAccessLevel() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getScreenName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLocation() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isContributorsEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public URL getProfileImageURL() {
				// TODO Auto-generated method stub
				try {
					return new URL("http://test");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			public URL getProfileImageUrlHttps() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URL getURL() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isProtected() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getFollowersCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Status getStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileBackgroundColor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileTextColor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileLinkColor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileSidebarFillColor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileSidebarBorderColor() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isProfileUseBackgroundImage() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isShowAllInlineMedia() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getFriendsCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Date getCreatedAt() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getFavouritesCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getUtcOffset() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getTimeZone() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileBackgroundImageUrl() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProfileBackgroundImageUrlHttps() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isProfileBackgroundTiled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getLang() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getStatusesCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isGeoEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isVerified() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isTranslator() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getListedCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isFollowRequestSent() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
	}

	@Override
	public boolean isRetweet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Status getRetweetedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] getContributors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getRetweetCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRetweetedByMe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserMentionEntity[] getUserMentionEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URLEntity[] getURLEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashtagEntity[] getHashtagEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Annotations getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MediaEntity[] getMediaEntities() {
		// TODO Auto-generated method stub
		return null;
	}


}
