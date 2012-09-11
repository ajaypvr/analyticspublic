package com.mapinfopage.util;

import java.util.ArrayList;
import java.util.List;

import com.mapinfopage.shared.TableInformationDO;
import com.mapinfopage.shared.MessageDO;
import com.mapinfopage.shared.VacationDO;
import com.mapinfopage.shared.TweetStatusDO;
import com.mapinfopage.shared.MapInformationDO;
import com.mapinfopage.shared.DestinationTweetCount;

public class DOTranslator {
	
	public static MessageDO getMessageDO(VacationDO vacationDO){
		MessageDO messageDO = new MessageDO();
		
		List<DestinationTweetCount> destinationTweetCountList = vacationDO.getDestinationTweetCount();
		
		List<MapInformationDO> mapInformationList = new ArrayList<MapInformationDO>();
		
		for(DestinationTweetCount destinationTweetCount : destinationTweetCountList){
			MapInformationDO mapInformationDO = getMapInformationDO(destinationTweetCount);
			mapInformationList.add(mapInformationDO);
		}
		
		messageDO.setMapInformationList(mapInformationList);
		
		List<TweetStatusDO> tweetStatusList = vacationDO.getTweetStatusList();
		List<TableInformationDO> tableInformationList = new ArrayList<TableInformationDO>();
		
		for(TweetStatusDO tweetStatusDO : tweetStatusList){
			TableInformationDO tableInformationDO = getTableInformationDO(tweetStatusDO);
			tableInformationList.add(tableInformationDO);
		}
		
		messageDO.setTableInformationList(tableInformationList);
		
		
		
		return messageDO;
	}
	
	
	public static List<TableInformationDO>  getTableInformationDO(List<TweetStatusDO> tweetStatusList){
		List<TableInformationDO> tableInformationList = new ArrayList<TableInformationDO>();
		
		for(TweetStatusDO tweetStatusDO : tweetStatusList){
			TableInformationDO tableInformationDO = getTableInformationDO(tweetStatusDO);
			tableInformationList.add(tableInformationDO);
		}
		
		return tableInformationList;
	}
	
	
	public static TableInformationDO getTableInformationDO(TweetStatusDO tweetStatusDO){
		TableInformationDO tableInformationDO = new TableInformationDO();
		
		tableInformationDO.setUserName(tweetStatusDO.getUserName());
		tableInformationDO.setImageURL(tweetStatusDO.getProfileImageURL());
		tableInformationDO.setText(tweetStatusDO.getTweetText());
		tableInformationDO.setTimeCreated(tweetStatusDO.getTimeCreated());
		
		return tableInformationDO;
		
	}

	
	
	
	public static MapInformationDO getMapInformationDO(DestinationTweetCount destinationTweetCount){
		MapInformationDO mapInformationDO = new MapInformationDO();
		
		mapInformationDO.setMarkerId(destinationTweetCount.getDestination());
		mapInformationDO.setDisplayText(destinationTweetCount.getDestination() + " " + destinationTweetCount.getTweetCount());
		mapInformationDO.setLatitude(destinationTweetCount.getLatitude());
		mapInformationDO.setLongitude(destinationTweetCount.getLongitude());
		
		return mapInformationDO;
	}
}
