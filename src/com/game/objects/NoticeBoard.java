package com.game.objects;

public class NoticeBoard {
	public int x,y,w,h;
	public String location;
	public WantedPoster[] posters;
	
	public NoticeBoard(int x,int y,String location,int playerLevel) {
		this.x = x;
		this.y = y;
		w = 64;
		h = 64;
		this.location = location;
		posters = new WantedPoster[] {
				new WantedPoster(playerLevel),new WantedPoster(playerLevel),new WantedPoster(playerLevel)
		};
		
		for(int i = 1; i < posters.length; i++) {
			if(posters[0].location.equals(posters[i].location)) {
				posters[i] = new WantedPoster(playerLevel);
			}
		}
		
		for(int c = 2; c < posters.length; c++) {
			if(posters[1].location.equals(posters[c].location)) {
				posters[c] = new WantedPoster(playerLevel);
			}
		}
	}
}
