package ca.sheridancollege.kau12280.beans;

public class Mission {
    private String missionTitle;
    private String gadgets;

    public Mission(String missionTitle, String gadgets) {
        this.missionTitle = missionTitle;
        this.gadgets = gadgets;
    }

	public String getMissionTitle() {
		return missionTitle;
	}

	public void setMissionTitle(String missionTitle) {
		this.missionTitle = missionTitle;
	}

	public String getGadgets() {
		return gadgets;
	}

	public void setGadgets(String gadgets) {
		this.gadgets = gadgets;
	}

    
}

